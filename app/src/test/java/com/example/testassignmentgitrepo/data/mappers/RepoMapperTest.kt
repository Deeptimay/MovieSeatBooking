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
    fun `mapFromDataModel returns expected MappedRepo`() {
        val repo = Repo(
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

        val mappedRepo = repoMapper.mapRepoToMappedRepoModel(repo)

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
        val repoList = listOf(
            Repo(
                1,
                "Repo Name 1",
                "Owner 1",
                "Description 1",
                Owner(),
                false,
                "git://github.com/repo1.git",
                "Description",
                true,
                "git://github.com/repo1.git",
                "2023-08-18T14:00:00Z",
                "2023-08-18T14:00:00Z"
            ),
            Repo(
                2,
                "Repo Name 2",
                "Owner 2",
                "Description 2",
                Owner(),
                false,
                "git://github.com/repo2.git",
                "Description",
                true,
                "git://github.com/repo2.git",
                "2023-08-18T12:00:00Z",
                "2023-08-18T12:00:00Z"
            )
        )

        val mappedRepoList = repoMapper.fromEntityList(repoList)

        assertEquals(2, mappedRepoList.size)
        assertEquals(1, mappedRepoList[0].id)
        assertEquals(2, mappedRepoList[1].id)
    }
}
