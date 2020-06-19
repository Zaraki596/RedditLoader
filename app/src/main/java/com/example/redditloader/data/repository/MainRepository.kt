package com.example.redditloader.data.repository

import com.example.redditloader.data.network.ApiHelper

class MainRepository(private val apiHelper : ApiHelper) {
        
    suspend fun getImages() = apiHelper.getImages()
}
