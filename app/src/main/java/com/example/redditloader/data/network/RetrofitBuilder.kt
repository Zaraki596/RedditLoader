package com.example.redditloader.data.network

import com.example.redditloader.data.api.RedditImageApiService
import com.example.redditloader.data.api.RedditImageApiService.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitBuilder {
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build() //Doesn't require the adapter
    }

    val redditImageApiService: RedditImageApiService =
        getRetrofit().create(RedditImageApiService::class.java)
}