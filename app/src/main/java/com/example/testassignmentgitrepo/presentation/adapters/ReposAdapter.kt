package com.example.testassignmentgitrepo.presentation.adapters

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testassignmentgitrepo.databinding.ItemTrendingRepoBinding
import com.example.testassignmentgitrepo.domain.models.MappedRepo


class ReposAdapter(
    private val repoClickListener: RepoClickListener,
    private val repoList: List<MappedRepo>
) :
    RecyclerView.Adapter<ReposAdapter.RepoListViewHolder>() {

    override fun getItemCount() = repoList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListViewHolder {
        val binding =
            ItemTrendingRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoListViewHolder(binding)
    }

    override fun onBindViewHolder(repoListViewHolder: RepoListViewHolder, position: Int) {
        with(repoListViewHolder) {
            with(repoList[position]) {
                Glide.with(itemView)
                    .load(this.owner?.avatarUrl)
                    .centerCrop()
                    .error(android.R.drawable.stat_notify_error)
                    .into(repoListViewHolder.binding.ivUserAvatar)

                val str = SpannableString((this.owner?.login ?: "") + " / " + this.name)
                str.setSpan(
                    StyleSpan(Typeface.NORMAL),
                    this.owner?.login?.length ?: 0,
                    str.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                repoListViewHolder.binding.tvUsername.text = str
                repoListViewHolder.binding.tvRepoDesc.text = this.description

                if (!this.language.isNullOrEmpty()) {
                    repoListViewHolder.binding.tvRepoLang.text = this.language
                    repoListViewHolder.binding.tvRepoLang.visibility = View.VISIBLE
                    repoListViewHolder.binding.ivDot.visibility = View.VISIBLE
                } else {
                    repoListViewHolder.binding.tvRepoLang.visibility = View.GONE
                    repoListViewHolder.binding.ivDot.visibility = View.GONE
                }

                repoListViewHolder.binding.tvRepoStars.text = this.stargazersCount.toString()
                repoListViewHolder.binding.tvRepoFork.text = this.forksCount.toString()

                ViewCompat.setTransitionName(
                    repoListViewHolder.binding.ivUserAvatar,
                    "avatar_${this.id}"
                )

                repoListViewHolder.itemView.setOnClickListener {
                    repoClickListener.onRepoClicked(this.id.toString())
                }
            }
        }
    }

    inner class RepoListViewHolder(val binding: ItemTrendingRepoBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface RepoClickListener {
        fun onRepoClicked(repoId: String)
    }
}