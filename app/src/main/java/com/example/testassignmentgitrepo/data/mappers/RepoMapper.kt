package com.example.testassignmentgitrepo.data.mappers

import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.data.models.Repo
import javax.inject.Inject

class RepoMapper @Inject constructor() : DataMapper<MappedRepo, Repo> {

    override fun mapRepoToMappedRepoModel(dataModel: Repo): MappedRepo {
        return MappedRepo(
            dataModel.id,
            dataModel.name,
            dataModel.description,
            dataModel.createdAt,
            dataModel.updatedAt,
            dataModel.pushedAt,
            dataModel.stargazersCount,
            dataModel.language,
            dataModel.forksCount,
            dataModel.gitUrl,
            dataModel.cloneUrl,
            dataModel.owner?.avatarUrl ?: "",
            dataModel.owner?.login ?: ""
        )
    }

    fun fromEntityList(initial: List<Repo>): List<MappedRepo> {
        return initial.map { mapRepoToMappedRepoModel(it) }
    }
}
