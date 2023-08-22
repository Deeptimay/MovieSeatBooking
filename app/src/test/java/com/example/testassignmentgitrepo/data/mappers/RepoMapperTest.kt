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

        assertEquals(1, mappedRepo.id)
        assertEquals("Repo Name", mappedRepo.name)
        assertEquals("Description", mappedRepo.description)
        assertEquals("2023-08-18T12:00:00Z", mappedRepo.createdAt)
        assertEquals("2023-08-18T13:00:00Z", mappedRepo.updatedAt)
        assertEquals("2023-08-18T14:00:00Z", mappedRepo.pushedAt)
        assertEquals(100, mappedRepo.stargazersCount)
        assertEquals("Kotlin", mappedRepo.language)
        assertEquals(50, mappedRepo.forksCount)
        assertEquals("git://github.com/repo.git", mappedRepo.gitUrl)
        assertEquals("https://github.com/repo.git", mappedRepo.cloneUrl)
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
            owner = Owner(),
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
            owner = Owner(),
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
