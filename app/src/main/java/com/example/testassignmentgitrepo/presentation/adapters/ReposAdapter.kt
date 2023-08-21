package com.example.testassignmentgitrepo.presentation.adapters

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.databinding.ItemTrendingRepoBinding


class ReposAdapter(
    private val repoClickListener: (String) -> Unit
) : ListAdapter<MappedRepo, ReposAdapter.RepoListViewHolder>(RepoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListViewHolder {
        val binding =
            ItemTrendingRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoListViewHolder, position: Int) {
        val repo = getItem(position)
        holder.bind(repo, repoClickListener)
    }

    inner class RepoListViewHolder(private val itemTrendingRepoBinding: ItemTrendingRepoBinding) :
        RecyclerView.ViewHolder(itemTrendingRepoBinding.root) {

        fun bind(repo: MappedRepo, repoClickListener: (String) -> Unit) {
            with(repo) {
                Glide.with(itemView)
                    .load(this.owner?.avatarUrl)
                    .centerCrop()
                    .error(android.R.drawable.stat_notify_error)
                    .into(itemTrendingRepoBinding.ivUserAvatar)

                val str = SpannableString((this.owner?.login ?: "") + " / " + this.name)
                str.setSpan(
                    StyleSpan(Typeface.NORMAL),
                    this.owner?.login?.length ?: 0,
                    str.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                itemTrendingRepoBinding.tvUsername.text = str
                itemTrendingRepoBinding.tvRepoDesc.text = this.description

                if (!this.language.isNullOrEmpty()) {
                    itemTrendingRepoBinding.tvRepoLang.text = this.language
                    itemTrendingRepoBinding.tvRepoLang.visibility = View.VISIBLE
                    itemTrendingRepoBinding.ivDot.visibility = View.VISIBLE
                } else {
                    itemTrendingRepoBinding.tvRepoLang.visibility = View.GONE
                    itemTrendingRepoBinding.ivDot.visibility = View.GONE
                }

                itemTrendingRepoBinding.tvRepoStars.text = this.stargazersCount.toString()
                itemTrendingRepoBinding.tvRepoFork.text = this.forksCount.toString()

                ViewCompat.setTransitionName(
                    itemTrendingRepoBinding.ivUserAvatar,
                    "avatar_${this.id}"
                )

                itemTrendingRepoBinding.root.setOnClickListener {
                    repoClickListener(this.id.toString())
                }
            }
        }
    }

    class RepoDiffCallback : DiffUtil.ItemCallback<MappedRepo>() {
        override fun areItemsTheSame(oldItem: MappedRepo, newItem: MappedRepo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MappedRepo, newItem: MappedRepo): Boolean {
            return oldItem == newItem
        }
    }
}