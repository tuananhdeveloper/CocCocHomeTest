package com.tuananh.simplebrowser.model

abstract class Article(
    val title: String,
    val description: String,
    val url: String,
    val imageUrl: String
) {
    abstract fun getType(): Int

    companion object {
        const val NORMAL_TYPE = 1;
        const val PODCAST_TYPE = 2;
    }
}