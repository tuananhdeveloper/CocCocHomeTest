package com.tuananh.simplebrowser.model

class NormalArticle(
    val author: String,
    title: String,
    description: String,
    url: String,
    imageUrl: String
): Article(title, description, url, imageUrl) {

    override fun getType(): Int {
        return NORMAL_TYPE;
    }
}