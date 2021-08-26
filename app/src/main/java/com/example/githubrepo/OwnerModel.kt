package com.example.githubrepo

import com.google.gson.annotations.SerializedName

data class OwnerModel (
    @SerializedName("avatar_url")
    val avatar_url: String? = null,
    @SerializedName("type")
    val type: String? = null
        )
