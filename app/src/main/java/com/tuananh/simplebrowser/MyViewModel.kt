package com.tuananh.simplebrowser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuananh.simplebrowser.model.Article
import com.tuananh.simplebrowser.model.NormalArticle
import com.tuananh.simplebrowser.model.PodcastArticle
import com.tuananh.simplebrowser.retrofit.Network
import com.tuananh.simplebrowser.retrofit.NewsAPIService
import com.tuananh.simplebrowser.retrofit.PodcastIndexAPIService
import kotlinx.coroutines.launch

class MyViewModel: ViewModel() {
    private val _articleData = MutableLiveData<List<Article>>()
    val articleData = _articleData
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage = _errorMessage
    private var newsAPIService: NewsAPIService = Network.getRetrofitA().create(NewsAPIService::class.java)
    private var podcastIndexAPIService: PodcastIndexAPIService = Network.getRetrofitB().create(PodcastIndexAPIService::class.java)
    private val xAuthDate = (System.currentTimeMillis() / 1000).toString()

    fun fetchData() {
        viewModelScope.launch {
            val newsAPIResponse = newsAPIService.getArticles(
                BuildConfig.NEWS_API_KEY,
                "tech",
                10
            )
            val podcastIndexAPIResponse = podcastIndexAPIService.getEpisodes(
                Network.userAgent,
                BuildConfig.PODCAST_INDEX_API_KEY,
                xAuthDate,
                Network.computePodcastIndexSignature(
                    BuildConfig.PODCAST_INDEX_API_KEY,
                    BuildConfig.API_SECRET,
                    xAuthDate
                )
            )
            val normalArticles = mutableListOf<NormalArticle>()
            val podcastArticles = mutableListOf<PodcastArticle>()

            if (newsAPIResponse.isSuccessful) {
                newsAPIResponse.body()?.let {
                    for(article in it.articles) {
                        val normalArticle = NormalArticle(
                            article.author ?: "",
                            article.title ?: "",
                            article.description ?: "",
                            article.url ?: "",
                            article.urlToImage ?: ""
                        )
                        normalArticles.add(normalArticle)
                    }
                }
            }
            else {
                _errorMessage.value = newsAPIResponse.errorBody()?.string()
            }

            if (podcastIndexAPIResponse.isSuccessful) {
                podcastIndexAPIResponse.body()?.let {
                    for (article in it.items) {
                        val podcastArticle = PodcastArticle(
                            article.enclosureUrl ?: "",
                            article.title ?: "",
                            article.description ?: "",
                            article.link ?: "",
                            article.image ?: ""
                        )
                        podcastArticles.add(podcastArticle)
                    }
                }
            }
            else {
                _errorMessage.value = podcastIndexAPIResponse.errorBody()?.string()
            }

            _articleData.value = mutableListOf<Article>().apply {
                addAll(normalArticles)
                addAll(podcastArticles)
            }
        }
    }
}