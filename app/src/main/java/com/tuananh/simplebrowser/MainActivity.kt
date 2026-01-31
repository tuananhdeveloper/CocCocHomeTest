package com.tuananh.simplebrowser

import android.app.AlertDialog
import android.content.Intent
import androidx.activity.viewModels
import com.tuananh.simplebrowser.adapter.MyAdapter
import com.tuananh.simplebrowser.databinding.ActivityMainBinding
import com.tuananh.simplebrowser.model.Article
import com.tuananh.simplebrowser.retrofit.NetworkUtil

class MainActivity : BaseActivity() {
    private val viewModel: MyViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        MyAdapter {
            onItemClick(it)
        }
    }

    override fun initLayout() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        fetchData()
    }

    private fun initView() {
        binding.rvArticles.adapter = adapter
    }

    private fun fetchData() {
        with(viewModel) {
            if (NetworkUtil.isInternetAvailable(this@MainActivity)) {
                fetchData()
            }
            else {
                showPopup(getString(R.string.text_no_internet_connection)) {
                    finish()
                }
            }
            articleData.observe(this@MainActivity) {
                adapter.updateData(it)
            }
            errorMessage.observe(this@MainActivity) {
                showPopup(it)
            }
            showingLoading.observe(this@MainActivity) {
                if (it) {
                    showProgressBar()
                }
                else {
                    hideProgressBar()
                }
            }
        }
    }

    private fun onItemClick(article: Article) {
        if (!article.url.isEmpty()) {
            startActivity(
                Intent(this, WebViewActivity::class.java).apply {
                    putExtra(WebViewActivity.URL_KEY, article.url)
                }
            )
        }
        else {
            showPopup(getString(R.string.text_url_is_empty))
        }
    }
}