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
import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.databinding.FragmentRepositoryDetailsBinding
import com.example.testassignmentgitrepo.presentation.homeScreen.ReposViewModel.Companion.SELECTED_REPO_ITEM
import com.example.testassignmentgitrepo.presentation.ui.UiState
import com.example.testassignmentgitrepo.presentation.util.DateUtility
import com.example.testassignmentgitrepo.presentation.util.hide
import com.example.testassignmentgitrepo.presentation.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RepoDetailsFragment : Fragment() {

    private var _fragmentRepositoryDetailsBinding: FragmentRepositoryDetailsBinding? = null
    private val fragmentRepositoryDetailsBinding get() = _fragmentRepositoryDetailsBinding!!

    private val repoDetailsViewModel: RepoDetailsViewModel by lazy {
        ViewModelProvider(this)[RepoDetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _fragmentRepositoryDetailsBinding =
            FragmentRepositoryDetailsBinding.inflate(inflater, container, false)

        observeState()

        requireArguments().getString(SELECTED_REPO_ITEM)?.let {
            repoDetailsViewModel.getRepoDetails(it)
        }

        fragmentRepositoryDetailsBinding.layoutError.lookUpButton.setOnClickListener {
            repoDetailsViewModel.getRepoDetails(repoDetailsViewModel.repoId)
        }

        return fragmentRepositoryDetailsBinding.root
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                repoDetailsViewModel.repoDetailsFlow.collect { uiState ->
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
            .into(fragmentRepositoryDetailsBinding.detailsLayout.ivUserAvatar)

        val str = SpannableString(
            (repo.owner?.login ?: "") + " / " + repo.name
        )
        str.setSpan(
            StyleSpan(Typeface.NORMAL),
            repo.owner?.login?.length ?: 0,
            str.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        fragmentRepositoryDetailsBinding.detailsLayout.tvUsername.text = str
        fragmentRepositoryDetailsBinding.detailsLayout.tvRepoDesc.text = repo.description
        fragmentRepositoryDetailsBinding.detailsLayout.tvRepoLang.text = repo.language
        fragmentRepositoryDetailsBinding.detailsLayout.tvRepoStars.text =
            repo.stargazersCount.toString()
        fragmentRepositoryDetailsBinding.detailsLayout.tvRepoFork.text =
            repo.forksCount.toString()

        fragmentRepositoryDetailsBinding.detailsLayout.tvCreatedAt.text = buildString {
            append("Created At: ")
            append(repo.createdAt?.let {
                DateUtility.formatDate(
                    it
                )
            })
        }
        fragmentRepositoryDetailsBinding.detailsLayout.tvUpdatedAt.text = buildString {
            append("Updated At: ")
            append(repo.updatedAt?.let {
                DateUtility.formatDate(
                    it
                )
            })
        }
        fragmentRepositoryDetailsBinding.detailsLayout.tvPushedAt.text = buildString {
            append("Pushed At: ")
            append(repo.pushedAt?.let {
                DateUtility.formatDate(
                    it
                )
            })
        }

        if (!repo.language.isNullOrEmpty()) {
            fragmentRepositoryDetailsBinding.detailsLayout.tvRepoLang.text = repo.language
            fragmentRepositoryDetailsBinding.detailsLayout.ivDot.visibility = View.VISIBLE
            fragmentRepositoryDetailsBinding.detailsLayout.tvRepoLang.visibility = View.VISIBLE
        } else {
            fragmentRepositoryDetailsBinding.detailsLayout.ivDot.visibility = View.GONE
            fragmentRepositoryDetailsBinding.detailsLayout.tvRepoLang.visibility = View.GONE
        }

        if (!repo.cloneUrl.isNullOrEmpty()) {
            fragmentRepositoryDetailsBinding.detailsLayout.tvCloneUrl.text = buildString {
                append("Clone Url: ")
                append(repo.cloneUrl)
            }
            fragmentRepositoryDetailsBinding.detailsLayout.tvCloneUrl.visibility = View.VISIBLE
        } else {
            fragmentRepositoryDetailsBinding.detailsLayout.tvCloneUrl.visibility = View.GONE
        }

        if (!repo.gitUrl.isNullOrEmpty()) {
            fragmentRepositoryDetailsBinding.detailsLayout.tvGitUrl.text = buildString {
                append("Git Url: ")
                append(repo.gitUrl)
            }
            fragmentRepositoryDetailsBinding.detailsLayout.tvGitUrl.visibility = View.VISIBLE
        } else {
            fragmentRepositoryDetailsBinding.detailsLayout.tvGitUrl.visibility = View.GONE
        }

        ViewCompat.setTransitionName(
            this@RepoDetailsFragment.fragmentRepositoryDetailsBinding.detailsLayout.ivUserAvatar,
            "avatar_${repo.id}"
        )
    }

    private fun displayErrorState() {
        fragmentRepositoryDetailsBinding.detailsLayout.clDetailsLayout.hide()
        fragmentRepositoryDetailsBinding.loadingLayout.clDetailsLoading.hide()
        fragmentRepositoryDetailsBinding.layoutError.clErrorMain.show()
    }

    private fun displayLoadingState() {
        fragmentRepositoryDetailsBinding.detailsLayout.clDetailsLayout.hide()
        fragmentRepositoryDetailsBinding.loadingLayout.clDetailsLoading.show()
        fragmentRepositoryDetailsBinding.layoutError.clErrorMain.hide()
    }

    private fun hideLoadingState() {
        fragmentRepositoryDetailsBinding.detailsLayout.clDetailsLayout.show()
        fragmentRepositoryDetailsBinding.loadingLayout.clDetailsLoading.hide()
        fragmentRepositoryDetailsBinding.layoutError.clErrorMain.hide()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigateUp()
        }
    }
}