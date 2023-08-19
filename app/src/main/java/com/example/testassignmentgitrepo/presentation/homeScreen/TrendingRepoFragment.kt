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
import com.example.testassignmentgitrepo.databinding.FragmentTrendingRepositoryBinding
import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.presentation.adapters.ReposAdapter
import com.example.testassignmentgitrepo.presentation.homeScreen.ReposViewModel.Companion.SELECTED_REPO_ITEM
import com.example.testassignmentgitrepo.presentation.util.UiState
import com.example.testassignmentgitrepo.presentation.util.hide
import com.example.testassignmentgitrepo.presentation.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrendingRepoFragment : Fragment(), ReposAdapter.RepoClickListener {

    private var _fragmentTrendingRepositoryBinding: FragmentTrendingRepositoryBinding? = null
    private val fragmentTrendingRepositoryBinding get() = _fragmentTrendingRepositoryBinding!!

    private val reposViewModel: ReposViewModel by lazy {
        ViewModelProvider(this)[ReposViewModel::class.java]
    }
    private lateinit var reposAdapter: ReposAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentTrendingRepositoryBinding =
            FragmentTrendingRepositoryBinding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                reposViewModel.repoFlow.collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            displayLoadingState()
                        }

                        is UiState.Error -> {
                            displayErrorState()
                        }

                        is UiState.Success<*> -> {
                            hideLoadingState()
                            val mappedRepoList = uiState.content as? List<MappedRepo>
                            mappedRepoList?.let { setupAdapter(it) }
                        }
                    }
                }
            }
        }

        reposViewModel.getRepoList()
        return fragmentTrendingRepositoryBinding.root
    }


    private fun setupAdapter(mappedRepos: List<MappedRepo>) {
        reposAdapter = ReposAdapter(this, mappedRepos)
        fragmentTrendingRepositoryBinding.apply {
            fragmentTrendingRepositoryBinding.rvRepository.apply {
                this.adapter = reposAdapter
            }
        }
    }

    private fun displayErrorState() {
        fragmentTrendingRepositoryBinding.layoutError.clErrorMain.show()
        fragmentTrendingRepositoryBinding.rvRepository.hide()
        fragmentTrendingRepositoryBinding.loadingLayout.clDetailsLoading.hide()
    }

    private fun displayLoadingState() {
        fragmentTrendingRepositoryBinding.layoutError.clErrorMain.hide()
        fragmentTrendingRepositoryBinding.rvRepository.hide()
        fragmentTrendingRepositoryBinding.loadingLayout.clDetailsLoading.show()
    }

    private fun hideLoadingState() {
        fragmentTrendingRepositoryBinding.layoutError.clErrorMain.hide()
        fragmentTrendingRepositoryBinding.rvRepository.show()
        fragmentTrendingRepositoryBinding.loadingLayout.clDetailsLoading.hide()
    }

    override fun onRepoClicked(repoId: String) {
        val bundle = Bundle().apply {
            putString(SELECTED_REPO_ITEM, repoId)
        }
        findNavController().navigate(
            R.id.action_trendingRepoFragment_to_repoDetailsFragment,
            bundle
        )
    }
}