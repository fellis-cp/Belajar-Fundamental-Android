package com.dicoding.githubhanif.api.retrofit

import com.dicoding.githubhanif.api.model.ResponseDetailUser
import com.dicoding.githubhanif.api.model.ResponseUserGithub
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUser() : MutableList<ResponseUserGithub.Item>


    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getDetailUser(@Path("username" )username: String) : ResponseDetailUser




}