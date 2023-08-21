package com.example.testassignmentgitrepo.presentation.adapters

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.databinding.ItemTrendingRepoBinding

class RepoListViewHolder(private val itemTrendingRepoBinding: ItemTrendingRepoBinding) :
    RecyclerView.ViewHolder(itemTrendingRepoBinding.root) {

    fun bind(repo: MappedRepo, repoClickListener: (String) -> Unit) {
        with(repo) {
            Glide.with(itemView)
                .load(this.ownerAvatar)
                .centerCrop()
                .error(android.R.drawable.stat_notify_error)
                .into(itemTrendingRepoBinding.ivUserAvatar)

            val str = SpannableString((this.ownerName) + " / " + this.name)
            str.setSpan(
                StyleSpan(Typeface.NORMAL),
                this.ownerName?.length ?: 0,
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

            itemTrendingRepoBinding.root.setOnClickListener {
                repoClickListener(this.id.toString())
            }
        }
    }
}