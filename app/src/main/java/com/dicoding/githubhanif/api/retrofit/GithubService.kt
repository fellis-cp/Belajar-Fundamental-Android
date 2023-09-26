package com.dicoding.githubhanif.api.retrofit

import com.dicoding.githubhanif.api.model.ResponseDetailUser
import com.dicoding.githubhanif.api.model.ResponseUserGithub
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface GithubService {
    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUser() : MutableList<ResponseUserGithub.Item>

    @JvmSuppressWildcards
    @GET("search/users")
    suspend fun searchUserGithub(
        @QueryMap params: Map<String, Any>,
        ): ResponseUserGithub

    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getDetailUser(@Path("username" )username: String) : ResponseDetailUser

    @JvmSuppressWildcards
    @GET("/users/{username}/following")
    suspend fun getFollowing(@Path("username" )username: String) : MutableList<ResponseUserGithub.Item>

    @JvmSuppressWildcards
    @GET("/users/{username}/followers")
    suspend fun getFollowers(@Path("username" )username: String) : MutableList<ResponseUserGithub.Item>




}