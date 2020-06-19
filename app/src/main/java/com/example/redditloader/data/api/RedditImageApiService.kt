package com.example.redditloader.data.api

import com.example.redditloader.data.model.RedditResponse
import retrofit2.http.GET

interface RedditImageApiService {
    companion object {
        val BASE_URL = "https://www.reddit.com/"
    }

    @GET("r/images/hot.json")
    suspend fun getImagesResponse(): RedditResponse
}