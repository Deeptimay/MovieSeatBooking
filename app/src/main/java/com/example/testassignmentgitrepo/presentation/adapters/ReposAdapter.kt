package com.example.testassignmentgitrepo.presentation.adapters

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testassignmentgitrepo.databinding.ItemTrendingRepoBinding
import com.example.testassignmentgitrepo.domain.models.MappedRepo


class ReposAdapter(private val onItemClickListener: OnItemClickListener) :
    PagingDataAdapter<MappedRepo, ReposAdapter.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<MappedRepo>() {
            override fun areItemsTheSame(oldItem: MappedRepo, newItem: MappedRepo) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MappedRepo, newItem: MappedRepo) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemTrendingRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { repo ->
            with(holder) {
                itemView.tag = repo
                if (repo != null) {
                    bind(createOnClickListener(binding, repo, position), repo)
                }
            }
        }
    }

    private fun createOnClickListener(
        binding: ItemTrendingRepoBinding,
        repo: MappedRepo,
        position: Int
    ): View.OnClickListener {
        return View.OnClickListener {
            snapshot()[position]?.let { onItemClickListener.onItemClicked(it.id.toString()) }
        }
    }

    class ViewHolder(val binding: ItemTrendingRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, repo: MappedRepo) {

            binding.apply {

                Glide.with(itemView)
                    .load(repo.owner?.avatarUrl)
                    .centerCrop()
                    .error(android.R.drawable.stat_notify_error)
                    .into(ivUserAvatar)

                val str = SpannableString((repo.owner?.login ?: "") + " / " + repo.name)
                str.setSpan(
                    StyleSpan(Typeface.NORMAL),
                    repo.owner?.login?.length ?: 0,
                    str.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                tvUsername.text = str
                tvRepoDesc.text = repo.description
                tvRepoLang.text = repo.language
                tvRepoStars.text = repo.stargazersCount.toString()
                tvRepoFork.text = repo.forksCount.toString()

                ViewCompat.setTransitionName(this.ivUserAvatar, "avatar_${repo.id}")

                root.setOnClickListener(listener)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(repoId: String)
    }
}