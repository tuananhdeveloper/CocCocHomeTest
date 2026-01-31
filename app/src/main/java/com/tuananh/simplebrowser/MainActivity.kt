package com.tuananh.simplebrowser

import android.app.AlertDialog
import android.content.Intent
import androidx.activity.viewModels
import com.tuananh.simplebrowser.adapter.MyAdapter
import com.tuananh.simplebrowser.databinding.ActivityMainBinding
import com.tuananh.simplebrowser.model.Article

class MainActivity : BaseActivity() {
    private val viewModel: MyViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        MyAdapter {
            onItemClick(it)
        }
    }

    private val dialog by lazy {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setPositiveButton("OK", null)
            .create()
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
            fetchData()
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

    private fun showPopup(message: String) {
        dialog.setMessage(message)
        if (!dialog.isShowing) {
            dialog.show()
        }
    }
}