package com.example.testassignmentgitrepo.data.mappers

import com.example.testassignmentgitrepo.data.models.Owner
import com.example.testassignmentgitrepo.data.models.Repo
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RepoMapperTest {

    private lateinit var repoMapper: RepoMapper

    @Before
    fun setUp() {
        repoMapper = RepoMapper()
    }

    @Test
    fun `mapRepoToMappedRepoModel returns expected MappedRepo`() {

        val mappedRepo = repoMapper.mapRepoToMappedRepoModel(repoFirst)

        assertEquals(repoFirst.id, mappedRepo.id)
        assertEquals(repoFirst.name, mappedRepo.name)
        assertEquals(repoFirst.description, mappedRepo.description)
        assertEquals(repoFirst.createdAt, mappedRepo.createdAt)
        assertEquals(repoFirst.updatedAt, mappedRepo.updatedAt)
        assertEquals(repoFirst.pushedAt, mappedRepo.pushedAt)
        assertEquals(repoFirst.stargazersCount, mappedRepo.stargazersCount)
        assertEquals(repoFirst.language, mappedRepo.language)
        assertEquals(repoFirst.forksCount, mappedRepo.forksCount)
        assertEquals(repoFirst.gitUrl, mappedRepo.gitUrl)
        assertEquals(repoFirst.cloneUrl, mappedRepo.cloneUrl)
        assertEquals(repoFirst.owner?.avatarUrl, mappedRepo.ownerAvatar)
        assertEquals(repoFirst.owner?.login, mappedRepo.ownerName)
    }

    @Test
    fun `fromEntityList returns expected list of MappedRepo`() {
        val repoList = listOf(repoFirst, repoSecond)

        val mappedRepoList = repoMapper.fromEntityList(repoList)

        assertEquals(2, mappedRepoList.size)
        assertEquals(1, mappedRepoList[0].id)
        assertEquals(2, mappedRepoList[1].id)
    }

    companion object {
        val repoFirst = Repo(
            id = 1,
            name = "Repo Name",
            owner = Owner(avatarUrl = "git://github.com/repo.git", login = "TestUser"),
            description = "Description",
            createdAt = "2023-08-18T12:00:00Z",
            updatedAt = "2023-08-18T13:00:00Z",
            pushedAt = "2023-08-18T14:00:00Z",
            stargazersCount = 100,
            language = "Kotlin",
            forksCount = 50,
            gitUrl = "git://github.com/repo.git",
            cloneUrl = "https://github.com/repo.git"
        )
        val repoSecond = Repo(
            id = 2,
            name = "Repo Name",
            owner = Owner(avatarUrl = "git://github.com/repo.git", login = "TestUser"),
            description = "Description",
            createdAt = "2023-08-18T12:00:00Z",
            updatedAt = "2023-08-18T13:00:00Z",
            pushedAt = "2023-08-18T14:00:00Z",
            stargazersCount = 100,
            language = "Kotlin",
            forksCount = 50,
            gitUrl = "git://github.com/repo.git",
            cloneUrl = "https://github.com/repo.git"
        )
    }
}
