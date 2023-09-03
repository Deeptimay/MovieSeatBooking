package com.example.testassignmentgitrepo.presentation.homeScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.testassignmentgitrepo.R
import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.databinding.FragmentTrendingRepositoryBinding
import com.example.testassignmentgitrepo.presentation.adapters.ReposAdapter
import com.example.testassignmentgitrepo.presentation.ui.UiState
import com.example.testassignmentgitrepo.presentation.util.hide
import com.example.testassignmentgitrepo.presentation.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrendingRepoFragment : Fragment() {

    private lateinit var fragmentTrendingRepositoryBinding: FragmentTrendingRepositoryBinding

    private val reposViewModel: ReposViewModel by lazy {
        ViewModelProvider(this)[ReposViewModel::class.java]
    }
    private lateinit var reposAdapter: ReposAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentTrendingRepositoryBinding =
            FragmentTrendingRepositoryBinding.inflate(inflater, container, false)

        setupAdapter()
        collectUiStates()
        reposViewModel.getRepoList()

        return fragmentTrendingRepositoryBinding.root
    }

    private fun setupAdapter() {
        reposAdapter = ReposAdapter { repoId: String ->
            run {
                val bundle = Bundle().apply {
                    putString(SELECTED_REPO_ITEM, repoId)
                }
                findNavController().navigate(
                    R.id.action_trendingRepoFragment_to_repoDetailsFragment,
                    bundle
                )
            }
        }
        fragmentTrendingRepositoryBinding.rvRepoList.adapter = reposAdapter
    }

    private fun collectUiStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                reposViewModel.repoFlow.collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> displayLoadingState()
                        is UiState.Error -> displayErrorState()
                        is UiState.Success<*> -> {
                            displaySuccessState()
                            val mappedRepoList = uiState.content as? List<MappedRepo>
                            mappedRepoList?.let {
                                reposAdapter.submitList(it)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun displayErrorState() {
        fragmentTrendingRepositoryBinding.apply {
            layoutError.clErrorMain.show()
            rvRepoList.hide()
            loadingLayout.clDetailsLoading.hide()
        }
    }

    private fun displayLoadingState() {
        fragmentTrendingRepositoryBinding.apply {
            layoutError.clErrorMain.hide()
            rvRepoList.hide()
            loadingLayout.clDetailsLoading.show()
        }
    }

    private fun displaySuccessState() {
        fragmentTrendingRepositoryBinding.apply {
            layoutError.clErrorMain.hide()
            rvRepoList.show()
            loadingLayout.clDetailsLoading.hide()
        }
    }

    private companion object {
        const val SELECTED_REPO_ITEM = "selected_repo_item"
    }
}
