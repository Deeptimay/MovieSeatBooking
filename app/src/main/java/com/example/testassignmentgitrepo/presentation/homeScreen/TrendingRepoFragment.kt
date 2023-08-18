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
import androidx.paging.LoadState
import com.example.testassignmentgitrepo.R
import com.example.testassignmentgitrepo.databinding.FragmentTrendingRepositoryBinding
import com.example.testassignmentgitrepo.presentation.adapters.ReposAdapter
import com.example.testassignmentgitrepo.presentation.adapters.ReposLoadStateAdapter
import com.example.testassignmentgitrepo.util.hide
import com.example.testassignmentgitrepo.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrendingRepoFragment : Fragment(), ReposAdapter.OnItemClickListener {

    private var _binding: FragmentTrendingRepositoryBinding? = null

    // with the backing property of the kotlin we extract
    // the non null value of the _binding
    private val binding get() = _binding!!

    private val reposViewModel: ReposViewModel by lazy {
        ViewModelProvider(this)[ReposViewModel::class.java]
    }
    private lateinit var reposAdapter: ReposAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrendingRepositoryBinding.inflate(inflater, container, false)

        displayLoadingState()
        setupAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                reposViewModel.repoListStateFlowData().collect { pagingData ->
                    reposAdapter.submitData(this@TrendingRepoFragment.lifecycle, pagingData)
                }
            }
        }

        return binding.root
    }


    private fun setupAdapter() {

        reposAdapter = ReposAdapter(this)
        binding.apply {
            binding.rvRepository.apply {
                setHasFixedSize(true)
                itemAnimator = null
                this.adapter = reposAdapter.withLoadStateHeaderAndFooter(
                    header = ReposLoadStateAdapter { reposAdapter.retry() },
                    footer = ReposLoadStateAdapter { reposAdapter.retry() }
                )
                postponeEnterTransition()
                viewTreeObserver.addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
            }

            binding.layoutError.lookUpButton.setOnClickListener {
                reposAdapter.retry()
            }
        }

        reposAdapter.addLoadStateListener { loadState ->
            binding.apply {
                when (loadState.source.refresh) {
                    is LoadState.Loading -> {
                        displayLoadingState()
                    }

                    is LoadState.NotLoading -> {
                        hideLoadingState()
                    }

                    is LoadState.Error -> {
                        displayErrorState()
                    }
                }
            }
        }
    }

    private fun displayErrorState() {
        binding.layoutError.clErrorMain.show()
        binding.rvRepository.hide()
        binding.loadingLayout.containerShimmer.hide()
        binding.loadingLayout.containerShimmer.stopShimmer()
    }

    private fun displayLoadingState() {
        binding.layoutError.clErrorMain.hide()
        binding.rvRepository.hide()
        binding.loadingLayout.containerShimmer.show()
        binding.loadingLayout.containerShimmer.startShimmer()
    }

    private fun hideLoadingState() {
        binding.layoutError.clErrorMain.hide()
        binding.rvRepository.show()
        binding.loadingLayout.containerShimmer.hide()
        binding.loadingLayout.containerShimmer.stopShimmer()
    }

    override fun onItemClicked(repoId: String) {
        val bundle = Bundle().apply {
            putString(SELECTED_REPO_ITEM, repoId)
        }
        findNavController().navigate(
            R.id.action_trendingRepoFragment_to_repoDetailsFragment,
            bundle
        )
    }
}