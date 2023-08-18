package com.example.testassignmentgitrepo.data.mappers

interface DataMapper<T, DataModel> {
    fun mapToDomainModel(model: T): DataModel
    fun mapFromDomainModel(dataModel: DataModel): T
}