package com.tuananh.simplebrowser.entity

import com.google.gson.annotations.SerializedName

data class NewsAPIResponse (
  @SerializedName("status") var status: String? = null,
  @SerializedName("totalResults") var totalResults : Int? = null,
  @SerializedName("articles") var articles: ArrayList<Articles> = arrayListOf()
)