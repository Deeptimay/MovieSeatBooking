package com.example.testassignmentgitrepo.presentation.homeScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import com.example.testassignmentgitrepo.databinding.FragmentTrendingRepositoryBinding
import com.example.testassignmentgitrepo.presentation.ReposViewModel
import com.example.testassignmentgitrepo.presentation.adapters.ReposAdapter
import com.example.testassignmentgitrepo.presentation.adapters.ReposLoadStateAdapter
import com.example.testassignmentgitrepo.util.hide
import com.example.testassignmentgitrepo.util.show

class TrendingRepoFragment : Fragment() {

    private var _binding: FragmentTrendingRepositoryBinding? = null

    // with the backing property of the kotlin we extract
    // the non null value of the _binding
    private val binding get() = _binding!!

    private lateinit var viewModel: ReposViewModel
    private lateinit var reposAdapter: ReposAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrendingRepositoryBinding.inflate(inflater, container, false)

        val toolbar: ActionBar? = (activity as AppCompatActivity).supportActionBar
        toolbar?.title = "My Title"
        toolbar?.setHomeButtonEnabled(false);
        viewModel = ViewModelProvider(this)[ReposViewModel::class.java]

        displayLoadingState()
        setupAdapter()

        viewModel.repos.observe(viewLifecycleOwner) {
            reposAdapter.submitData(this.lifecycle, it)
        }

        return binding.root
    }


    private fun setupAdapter() {

        reposAdapter = ReposAdapter()
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

                // no results found
//                if (loadState.source.refresh is LoadState.NotLoading &&
//                    loadState.append.endOfPaginationReached &&
//                    reposAdapter.itemCount < 1
//                ) {
//                    displayErrorState()
//                } else {
//                    hideLoadingState()
//                }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}