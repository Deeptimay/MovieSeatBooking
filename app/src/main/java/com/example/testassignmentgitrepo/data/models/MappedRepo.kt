package com.example.testassignmentgitrepo.data.models


data class MappedRepo(
    var id: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var createdAt: String? = null,
    var updatedAt: String? = null,
    var pushedAt: String? = null,
    var stargazersCount: Int? = null,
    var language: String? = null,
    var forksCount: Int? = null,
    var gitUrl: String? = null,
    var cloneUrl: String? = null,
    var ownerAvatar: String? = null,
    var ownerName: String? = null
)