package com.tuananh.simplebrowser.retrofit

import com.tuananh.simplebrowser.entity.PodcastIndexAPIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface PodcastIndexAPIService {
    @GET("/api/1.0/recent/episodes")
    suspend fun getEpisodes(
        @Header("User-Agent") userAgent: String,
        @Header("X-Auth-Key") apiKey: String,
        @Header("X-Auth-Date") xAuthDate: String,
        @Header("Authorization") authKey: String
    ): Response<PodcastIndexAPIResponse>
}