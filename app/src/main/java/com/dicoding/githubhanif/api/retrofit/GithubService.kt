package com.dicoding.githubhanif.api.retrofit

import com.dicoding.githubhanif.api.model.ResponseUserGithub
import retrofit2.http.GET

interface GithubService {
    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUser() : MutableList<ResponseUserGithub.Item>

}