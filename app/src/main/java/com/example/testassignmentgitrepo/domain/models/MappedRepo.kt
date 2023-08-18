package com.example.testassignmentgitrepo.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MappedRepo(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("owner") var owner: Owner? = Owner(),
    @SerializedName("description") var description: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("pushed_at") var pushedAt: String? = null,
    @SerializedName("stargazers_count") var stargazersCount: Int? = null,
    @SerializedName("language") var language: String? = null,
    @SerializedName("forks_count") var forksCount: Int? = null,
    @SerializedName("git_url") var gitUrl: String? = null,
    @SerializedName("clone_url") var cloneUrl: String? = null
) : Parcelable