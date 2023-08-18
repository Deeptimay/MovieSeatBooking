package com.example.testassignmentgitrepo.data.mappers

interface DataMapper<T, DataModel> {
    fun mapToDataModel(model: T): DataModel
    fun mapFromDataModel(dataModel: DataModel): T
}