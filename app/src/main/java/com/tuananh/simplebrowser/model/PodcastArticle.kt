package com.tuananh.simplebrowser.model

class PodcastArticle(
    val audioUrl: String,
    title: String,
    description: String,
    url: String,
    imageUrl: String
): Article(title, description, url, imageUrl) {

    override fun getType(): Int {
        return PODCAST_TYPE;
    }
}