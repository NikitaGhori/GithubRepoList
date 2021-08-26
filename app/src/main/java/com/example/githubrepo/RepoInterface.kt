package com.example.githubrepo

import retrofit2.Call
import retrofit2.http.GET

interface RepoInterface {
    @GET("repos")
    fun getRepoData(
    ): Call<List<GithubRepositoryResponse?>>

    companion object {

    const val BASE_URL = "https://api.github.com/users/defunkt/"
}
}

