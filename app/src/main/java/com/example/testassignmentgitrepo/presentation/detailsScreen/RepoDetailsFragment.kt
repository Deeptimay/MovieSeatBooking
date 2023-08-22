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
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.testassignmentgitrepo.R
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

    private lateinit var fragmentRepositoryDetailsBinding: FragmentRepositoryDetailsBinding

    private val repoDetailsViewModel: RepoDetailsViewModel by lazy {
        ViewModelProvider(this)[RepoDetailsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        fragmentRepositoryDetailsBinding =
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
                        is UiState.Loading -> displayLoadingState()
                        is UiState.Error -> displayErrorState()
                        is UiState.Success<*> -> {
                            displaySuccessState()
                            inflateData(uiState.content as MappedRepo)
                        }
                    }
                }
            }
        }
    }

    private fun inflateData(repo: MappedRepo) {
        fragmentRepositoryDetailsBinding.detailsLayout.apply {
            Glide.with(this@RepoDetailsFragment)
                .load(repo.ownerAvatar)
                .centerCrop()
                .error(android.R.drawable.stat_notify_error)
                .into(ivUserAvatar)

            val str = SpannableString("${repo.ownerName} / ${repo.name}")
            str.setSpan(
                StyleSpan(Typeface.NORMAL),
                repo.ownerName?.length ?: 0,
                str.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tvUsername.text = str
            tvRepoDesc.text = repo.description
            tvRepoLang.text = repo.language
            tvRepoStars.text = repo.stargazersCount.toString()
            tvRepoFork.text = repo.forksCount.toString()

            tvCreatedAt.text =
                getString(R.string.created_at, repo.createdAt?.let { DateUtility.formatDate(it) })
            tvUpdatedAt.text =
                getString(R.string.updated_at, repo.updatedAt?.let { DateUtility.formatDate(it) })
            tvPushedAt.text =
                getString(R.string.pushed_at, repo.pushedAt?.let { DateUtility.formatDate(it) })

            tvCloneUrl.text = getString(R.string.clone_url, repo.cloneUrl)
            tvGitUrl.text = getString(R.string.git_url, repo.gitUrl)

            if (!repo.language.isNullOrEmpty()) {
                tvRepoLang.text = repo.language
                ivDot.visibility = View.VISIBLE
                tvRepoLang.visibility = View.VISIBLE
            } else {
                ivDot.visibility = View.GONE
                tvRepoLang.visibility = View.GONE
            }
        }
    }

    private fun displayErrorState() {
        fragmentRepositoryDetailsBinding.apply {
            detailsLayout.clDetailsLayout.hide()
            loadingLayout.clDetailsLoading.hide()
            layoutError.clErrorMain.show()
        }
    }

    private fun displayLoadingState() {
        fragmentRepositoryDetailsBinding.apply {
            detailsLayout.clDetailsLayout.hide()
            loadingLayout.clDetailsLoading.show()
            layoutError.clErrorMain.hide()
        }
    }

    private fun displaySuccessState() {
        fragmentRepositoryDetailsBinding.apply {
            detailsLayout.clDetailsLayout.show()
            loadingLayout.clDetailsLoading.hide()
            layoutError.clErrorMain.hide()
        }
    }
}