package com.example.testassignmentgitrepo.presentation.detailsScreen

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.testassignmentgitrepo.databinding.FragmentRepositoryDetailsBinding
import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.presentation.util.UiState
import com.example.testassignmentgitrepo.presentation.homeScreen.ReposViewModel.Companion.SELECTED_REPO_ITEM
import com.example.testassignmentgitrepo.presentation.util.DateUtility
import com.example.testassignmentgitrepo.presentation.util.hide
import com.example.testassignmentgitrepo.presentation.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RepoDetailsFragment : Fragment() {

    private var _binding: FragmentRepositoryDetailsBinding? = null
    private val binding get() = _binding!!

    private val repoDetailsViewModel: RepoDetailsViewModel by lazy {
        ViewModelProvider(this)[RepoDetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoryDetailsBinding.inflate(inflater, container, false)

        observeState()

        arguments?.getString(SELECTED_REPO_ITEM)?.let {
            repoDetailsViewModel.getRepoDetails(it)
        }

        binding.layoutError.lookUpButton.setOnClickListener {
            repoDetailsViewModel.getRepoDetails(repoDetailsViewModel.repoId)
        }

        return binding.root
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                repoDetailsViewModel.repoDetailsStateFlowData().collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            displayLoadingState()
                        }

                        is UiState.Error -> {
                            displayErrorState()
                        }

                        is UiState.Success<*> -> {
                            hideLoadingState()
                            inflateData(uiState.content as MappedRepo)
                        }
                    }
                }
            }
        }
    }

    private fun inflateData(repo: MappedRepo) {

        Glide.with(this@RepoDetailsFragment).load(repo.owner?.avatarUrl)
            .centerCrop()
            .error(android.R.drawable.stat_notify_error)
            .into(binding.detailsLayout.ivUserAvatar)

        val str = SpannableString(
            (repo.owner?.login ?: "") + " / " + repo.name
        )
        str.setSpan(
            StyleSpan(Typeface.NORMAL),
            repo.owner?.login?.length ?: 0,
            str.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.detailsLayout.tvUsername.text = str
        binding.detailsLayout.tvRepoDesc.text = repo.description
        binding.detailsLayout.tvRepoLang.text = repo.language
        binding.detailsLayout.tvRepoStars.text =
            repo.stargazersCount.toString()
        binding.detailsLayout.tvRepoFork.text =
            repo.forksCount.toString()

        binding.detailsLayout.tvCreatedAt.text = buildString {
            append("Created At: ")
            append(repo.createdAt?.let {
                DateUtility.formatDate(
                    it
                )
            })
        }
        binding.detailsLayout.tvUpdatedAt.text = buildString {
            append("Updated At: ")
            append(repo.updatedAt?.let {
                DateUtility.formatDate(
                    it
                )
            })
        }
        binding.detailsLayout.tvPushedAt.text = buildString {
            append("Pushed At: ")
            append(repo.pushedAt?.let {
                DateUtility.formatDate(
                    it
                )
            })
        }

        binding.detailsLayout.tvCloneUrl.text = buildString {
            append("Clone Url: ")
            append(repo.cloneUrl)
        }

        binding.detailsLayout.tvGitUrl.text = buildString {
            append("Git Url: ")
            append(repo.gitUrl)
        }

        ViewCompat.setTransitionName(
            this@RepoDetailsFragment.binding.detailsLayout.ivUserAvatar,
            "avatar_${repo.id}"
        )
    }

    private fun displayErrorState() {
        binding.detailsLayout.clDetailsLayout.hide()
        binding.loadingLayout.clDetailsLoading.hide()
        binding.layoutError.clErrorMain.show()
    }

    private fun displayLoadingState() {
        binding.detailsLayout.clDetailsLayout.hide()
        binding.loadingLayout.clDetailsLoading.show()
        binding.layoutError.clErrorMain.hide()
    }

    private fun hideLoadingState() {
        binding.detailsLayout.clDetailsLayout.show()
        binding.loadingLayout.clDetailsLoading.hide()
        binding.layoutError.clErrorMain.hide()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigateUp()
        }
    }
}