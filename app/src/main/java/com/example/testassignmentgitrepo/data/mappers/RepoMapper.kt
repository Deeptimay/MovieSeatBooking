package com.example.testassignmentgitrepo.data.mappers

import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.data.models.Repo
import javax.inject.Inject

class RepoMapper @Inject constructor() : DataMapper<MappedRepo, Repo> {
    override fun mapToDataModel(model: MappedRepo): Repo {
        return Repo(
            id = model.id,
            name = model.name,
            owner = model.owner,
            description = model.description,
            createdAt = model.createdAt,
            updatedAt = model.updatedAt,
            pushedAt = model.pushedAt,
            stargazersCount = model.stargazersCount,
            language = model.language,
            forksCount = model.forksCount,
            gitUrl = model.gitUrl,
            cloneUrl = model.cloneUrl
        )
    }

    override fun mapFromDataModel(dataModel: Repo): MappedRepo {
        return MappedRepo(
            dataModel.id,
            dataModel.name,
            dataModel.owner,
            dataModel.description,
            dataModel.createdAt,
            dataModel.updatedAt,
            dataModel.pushedAt,
            dataModel.stargazersCount,
            dataModel.language,
            dataModel.forksCount,
            dataModel.gitUrl,
            dataModel.cloneUrl
        )
    }

    fun fromEntityList(initial: List<Repo>): List<MappedRepo> {
        return initial.map { mapFromDataModel(it) }
    }
}