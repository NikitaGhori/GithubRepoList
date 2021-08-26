package com.example.githubrepo

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.io.Serializable

data class GithubRepositoryResponse(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("full_name")
    val full_name: String? = null,
    @SerializedName("owner")
    val owner: OwnerModel? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("size")
    val size: Int? = null,
    @SerializedName("stargazers_count")
    val stargazers_count: Int? = null,
    @SerializedName("language")
    val language: String? = null,
    @SerializedName("forks")
    val forks: Int? = null,
    @SerializedName("open_issues")
    val open_issues: Int? = null,
    @SerializedName("watchers")
    val watchers: Int? = null,
    @SerializedName("default_branch")
    val default_branch: String? = null
)