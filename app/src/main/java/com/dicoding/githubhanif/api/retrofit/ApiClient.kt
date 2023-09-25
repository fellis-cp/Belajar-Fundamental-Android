package com.dicoding.githubhanif.api.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object ApiClient {
    // Your GitHub Personal Access Token
    private const val GITHUB_TOKEN = "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"

    private val okhttp = OkHttpClient.Builder()
        .apply {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(loggingInterceptor)

            // Add an interceptor to set the Authorization header with your GitHub token
            addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $GITHUB_TOKEN")
                    .build()
                chain.proceed(request)
            }
        }
        .readTimeout(25, TimeUnit.SECONDS)
        .writeTimeout(280, TimeUnit.SECONDS)
        .connectTimeout(50, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .client(okhttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val githubService = retrofit.create<GithubService>()
}
