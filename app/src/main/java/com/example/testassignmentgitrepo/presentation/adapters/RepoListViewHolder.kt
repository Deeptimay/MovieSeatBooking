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
                .load(ownerAvatar)
                .centerCrop()
                .error(android.R.drawable.stat_notify_error)
                .into(itemTrendingRepoBinding.ivUserAvatar)

            val str = SpannableString("$ownerName / $name")
            str.setSpan(
                StyleSpan(Typeface.NORMAL),
                ownerName?.length ?: 0,
                str.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            itemTrendingRepoBinding.tvUsername.text = str
            itemTrendingRepoBinding.tvRepoDesc.text = description

            itemTrendingRepoBinding.tvRepoLang.apply {
                if (!language.isNullOrEmpty()) {
                    text = language
                    visibility = View.VISIBLE
                } else {
                    visibility = View.GONE
                }
            }

            itemTrendingRepoBinding.ivDot.visibility =
                if (!language.isNullOrEmpty()) View.VISIBLE else View.GONE

            itemTrendingRepoBinding.tvRepoStars.text = stargazersCount.toString()
            itemTrendingRepoBinding.tvRepoFork.text = forksCount.toString()

            itemTrendingRepoBinding.root.setOnClickListener {
                repoClickListener(id.toString())
            }
        }
    }
}
