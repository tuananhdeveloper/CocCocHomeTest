package com.tuananh.simplebrowser.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest

object Network {
    private var retrofitA: Retrofit? = null
    private var retrofitB: Retrofit? = null
    private const val NEWS_API_BASE_URL = "https://newsapi.org"
    private const val PODCAST_INDEX_API_BASE_URL = "https://api.podcastindex.org"
    private const val ALGORITHM = "SHA-1"
    private const val PATTERN = "%02x"
    val userAgent = "MyApp/1.0 (Android; Mobile)"

    fun getRetrofitA(): Retrofit {
        if (retrofitA == null) {
            retrofitA = Retrofit.Builder()
                .baseUrl(NEWS_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitA!!
    }

    fun getRetrofitB(): Retrofit {
        if (retrofitB == null) {
            retrofitB = Retrofit.Builder()
                .baseUrl(PODCAST_INDEX_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitB!!
    }

    fun computePodcastIndexSignature(apiKey: String, apiSecret: String, epochSeconds: String): String {
        val base = apiKey + apiSecret + epochSeconds

        val sha1 = MessageDigest.getInstance(ALGORITHM)
        val hashBytes = sha1.digest(base.toByteArray(Charsets.UTF_8))

        return hashBytes.joinToString("") { PATTERN.format(it) }
    }
}