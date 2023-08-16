package com.example.testassignmentgitrepo.presentation.detailsScreen

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.databinding.FragmentRepositoryDetailsBinding
import com.example.testassignmentgitrepo.presentation.ReposViewModel
import com.example.testassignmentgitrepo.presentation.SELECTED_REPO_ITEM
import com.example.testassignmentgitrepo.retrofitSetup.BaseResponse
import com.example.testassignmentgitrepo.retrofitSetup.BaseResponse.Loading
import com.example.testassignmentgitrepo.util.DateUtility
import com.example.testassignmentgitrepo.util.hide
import com.example.testassignmentgitrepo.util.show
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RepoDetailsFragment : Fragment() {
    private var _binding: FragmentRepositoryDetailsBinding? = null

    // with the backing property of the kotlin we extract
    // the non null value of the _binding
    private val binding get() = _binding!!
    private lateinit var viewModel: ReposViewModel
    private lateinit var repoId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoryDetailsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[ReposViewModel::class.java]

        arguments?.getString(SELECTED_REPO_ITEM)?.let {
            val gson = Gson()
            val repo = gson.fromJson(it, MappedRepo::class.java)
            repoId = repo.id.toString()
            viewModel.getRepoDetails(repoId)
        }

        binding.layoutError.lookUpButton.setOnClickListener {
            viewModel.getRepoDetails(repoId)
        }

        observeAndInflateData()

        return binding.root
    }

    private fun observeAndInflateData() {
        viewModel.getRepoDetailsMutableLiveData()
            .observe(viewLifecycleOwner) { baseResponse: BaseResponse<MappedRepo>? ->

                when (baseResponse) {
                    is BaseResponse.Error -> {
                        binding.detailsLayout.clDetailsLayout.hide()
                        binding.loadingLayout.clDetailsLoading.hide()
                        binding.layoutError.clErrorMain.show()
                    }

                    is Loading -> {
                        binding.detailsLayout.clDetailsLayout.hide()
                        binding.loadingLayout.clDetailsLoading.show()
                        binding.layoutError.clErrorMain.hide()
                    }

                    null -> {
                        binding.detailsLayout.clDetailsLayout.hide()
                        binding.loadingLayout.clDetailsLoading.hide()
                        binding.layoutError.clErrorMain.hide()
                    }

                    is BaseResponse.Success -> {

                        binding.detailsLayout.clDetailsLayout.show()
                        binding.loadingLayout.clDetailsLoading.hide()
                        binding.layoutError.clErrorMain.hide()

                        baseResponse?.let { response ->
                            response.status?.let { repoStatus ->
                                if (repoStatus) {
                                    response.data?.let { repo ->
                                        Glide.with(this).load(repo.owner?.avatarUrl).centerCrop()
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
                                            append(repo.cloneUrl?.let {
                                                SpannableStringBuilder().urlSpan(
                                                    it
                                                )
                                            })
                                        }

                                        binding.detailsLayout.tvGitUrl.text = buildString {
                                            append("Git Url: ")
                                            append(repo.gitUrl?.let {
                                                SpannableStringBuilder().urlSpan(
                                                    it
                                                )
                                            })
                                        }

                                        ViewCompat.setTransitionName(
                                            this.binding.detailsLayout.ivUserAvatar,
                                            "avatar_${repo.id}"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
    }

    private fun SpannableStringBuilder.urlSpan(value: String): SpannableStringBuilder {
        val start = length
        this.append(value)
        val end = length
        this.setSpan(URLSpan(value), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigateUp()
        }
    }
}