package com.example.testassignmentgitrepo.data.mappers

interface DataMapper<T, DataModel> {
    fun mapRepoToMappedRepoModel(dataModel: DataModel): T
}