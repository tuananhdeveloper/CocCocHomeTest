package com.tuananh.simplebrowser.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest

object Network {
    private var retrofitA: Retrofit? = null
    private var retrofitB: Retrofit? = null
    val userAgent = "MyApp/1.0 (Android; Mobile)"

    fun getRetrofitA(): Retrofit {
        if (retrofitA == null) {
            retrofitA = Retrofit.Builder()
                .baseUrl("https://newsapi.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitA!!
    }

    fun getRetrofitB(): Retrofit {
        if (retrofitB == null) {
            retrofitB = Retrofit.Builder()
                .baseUrl("https://api.podcastindex.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitB!!
    }

    fun computePodcastIndexSignature(apiKey: String, apiSecret: String, epochSeconds: String): String {
        // Step 1: Concatenate as described
        val base = apiKey + apiSecret + epochSeconds

        // Step 2: SHA-1 hash
        val sha1 = MessageDigest.getInstance("SHA-1")
        val hashBytes = sha1.digest(base.toByteArray(Charsets.UTF_8))

        // Step 3: Convert to lowercase hexadecimal string
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}