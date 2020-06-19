package com.example.redditloader.data.network

import com.example.redditloader.data.api.RedditImageApiService

class ApiHelper(private val redditImageApiService: RedditImageApiService) {
    suspend fun getImages() = redditImageApiService.getImagesResponse()
}
