package com.tuananh.simplebrowser.entity

import com.google.gson.annotations.SerializedName

data class PodcastIndexAPIResponse (
    @SerializedName("status") var status: String? = null,
    @SerializedName("items") var items: ArrayList<Episode> = arrayListOf(),
    @SerializedName("count") var count: Int? = null,
    @SerializedName("max") var max: Int? = null,
    @SerializedName("description" ) var description : String? = null
)