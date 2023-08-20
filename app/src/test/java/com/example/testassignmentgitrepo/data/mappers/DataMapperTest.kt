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
    fun `test mapToDataModel`() {
        val repo = Repo(id = 1, name = "Test")
        val mappedRepo = userDataMapper.mapToDataModel(repo)

        assertEquals(repo.id, mappedRepo.id)
        assertEquals(repo.name, mappedRepo.name)
    }

    @Test
    fun `test mapFromDataModel`() {

        val mappedRepo = MappedRepo(id = 2, name = "Test")
        val repo = userDataMapper.mapFromDataModel(mappedRepo)

        assertEquals(mappedRepo.id, repo.id)
        assertEquals(mappedRepo.name, repo.name)
    }


    private class RepoDataMapperImpl : DataMapper<Repo, MappedRepo> {
        override fun mapToDataModel(model: Repo): MappedRepo {
            return MappedRepo(id = model.id, name = model.name)
        }

        override fun mapFromDataModel(dataModel: MappedRepo): Repo {
            return Repo(id = dataModel.id, name = dataModel.name)
        }
    }
}
