package com.example.testassignmentgitrepo.data.mappers

import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.data.models.Repo
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DataMapperTest {

    private lateinit var userDataMapper: DataMapper<Repo, MappedRepo>

    @Before
    fun setUp() {
        userDataMapper = RepoDataMapperImpl()
    }

    @Test
    fun `test mapFromDataModel`() {

        val mappedRepo = MappedRepo(id = 2, name = "Test")
        val repo = userDataMapper.mapRepoToMappedRepoModel(mappedRepo)

        assertEquals(mappedRepo.id, repo.id)
        assertEquals(mappedRepo.name, repo.name)
    }


    private class RepoDataMapperImpl : DataMapper<Repo, MappedRepo> {

        override fun mapRepoToMappedRepoModel(dataModel: MappedRepo): Repo {
            return Repo(id = dataModel.id, name = dataModel.name)
        }
    }
}
