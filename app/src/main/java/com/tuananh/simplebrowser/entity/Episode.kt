package com.tuananh.simplebrowser.entity

import com.google.gson.annotations.SerializedName

data class Episode (
  @SerializedName("id") var id: Long? = null,
  @SerializedName("title") var title: String? = null,
  @SerializedName("link") var link: String? = null,
  @SerializedName("description") var description: String? = null,
  @SerializedName("guid") var guid: String? = null,
  @SerializedName("datePublished") var datePublished: Int? = null,
  @SerializedName("datePublishedPretty") var datePublishedPretty: String? = null,
  @SerializedName("dateCrawled") var dateCrawled: Int? = null,
  @SerializedName("enclosureUrl") var enclosureUrl: String? = null,
  @SerializedName("enclosureType") var enclosureType: String? = null,
  @SerializedName("enclosureLength") var enclosureLength: Int? = null,
  @SerializedName("explicit") var explicit: Int? = null,
  @SerializedName("episode") var episode: Int? = null,
  @SerializedName("episodeType") var episodeType: String? = null,
  @SerializedName("season") var season: Int? = null,
  @SerializedName("image") var image: String? = null,
  @SerializedName("feedItunesId") var feedItunesId: Int? = null,
  @SerializedName("feedImage") var feedImage: String? = null,
  @SerializedName("feedId") var feedId: Int? = null,
  @SerializedName("feedTitle") var feedTitle: String? = null,
  @SerializedName("feedLanguage") var feedLanguage: String? = null
)