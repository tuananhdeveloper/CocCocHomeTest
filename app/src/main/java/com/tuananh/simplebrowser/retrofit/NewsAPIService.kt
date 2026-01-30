package com.tuananh.simplebrowser.retrofit

import com.tuananh.simplebrowser.entity.NewsAPIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsAPIService {
    @GET("/v2/everything")
    suspend fun getArticles(
        @Header("Authorization") authKey: String,
        @Query("q") query: String,
        @Query("pageSize") pageSize: Int
    ): Response<NewsAPIResponse>
}