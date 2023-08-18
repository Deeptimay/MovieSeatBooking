package com.example.testassignmentgitrepo.data.mappers

import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.domain.models.Repo
import javax.inject.Inject

class RepoMapper @Inject constructor() : DataMapper<MappedRepo, Repo> {
    override fun mapToDomainModel(model: MappedRepo): Repo {
        return Repo()
    }

    override fun mapFromDomainModel(domainModel: Repo): MappedRepo {
        return MappedRepo(
            domainModel.id,
            domainModel.name,
            domainModel.owner,
            domainModel.description,
            domainModel.createdAt,
            domainModel.updatedAt,
            domainModel.pushedAt,
            domainModel.stargazersCount,
            domainModel.language,
            domainModel.forksCount,
            domainModel.gitUrl,
            domainModel.cloneUrl
        )
    }

    fun fromEntityList(initial: List<Repo>): List<MappedRepo> {
        return initial.map { mapFromDomainModel(it) }
    }
}