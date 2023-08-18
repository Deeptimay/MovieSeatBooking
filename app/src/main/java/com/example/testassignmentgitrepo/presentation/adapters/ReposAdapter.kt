package com.example.testassignmentgitrepo.presentation.adapters

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
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

    override fun onBindViewHolder(holder: RepoListViewHolder, position: Int) {
        with(holder) {
            with(repoList[position]) {
                Glide.with(itemView)
                    .load(this.owner?.avatarUrl)
                    .centerCrop()
                    .error(android.R.drawable.stat_notify_error)
                    .into(holder.binding.ivUserAvatar)

                val str = SpannableString((this.owner?.login ?: "") + " / " + this.name)
                str.setSpan(
                    StyleSpan(Typeface.NORMAL),
                    this.owner?.login?.length ?: 0,
                    str.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                holder.binding.tvUsername.text = str
                holder.binding.tvRepoDesc.text = this.description
                holder.binding.tvRepoLang.text = this.language
                holder.binding.tvRepoStars.text = this.stargazersCount.toString()
                holder.binding.tvRepoFork.text = this.forksCount.toString()

                ViewCompat.setTransitionName(holder.binding.ivUserAvatar, "avatar_${this.id}")

                holder.itemView.setOnClickListener {
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