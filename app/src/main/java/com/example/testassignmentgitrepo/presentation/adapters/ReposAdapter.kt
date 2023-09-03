package com.example.testassignmentgitrepo.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.databinding.ItemTrendingRepoBinding

class ReposAdapter(
    private val repoClickListener: (String) -> Unit
) : ListAdapter<MappedRepo, RepoListViewHolder>(RepoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListViewHolder {
        val binding =
            ItemTrendingRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoListViewHolder, position: Int) {
        val repo = getItem(position)
        holder.bind(repo, repoClickListener)
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
