package com.tuananh.simplebrowser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.tuananh.simplebrowser.adapter.MyAdapter
import com.tuananh.simplebrowser.databinding.ActivityMainBinding
import com.tuananh.simplebrowser.model.Article
import android.app.AlertDialog
import android.content.Intent

class MainActivity : AppCompatActivity() {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        }
    }

    private fun onItemClick(article: Article) {
        startActivity(
            Intent(this, WebViewActivity::class.java).apply {
                putExtra(WebViewActivity.URL_KEY, article.url)
            }
        )
    }

    private fun showPopup(message: String) {
        dialog.setMessage(message)
        if (!dialog.isShowing) {
            dialog.show()
        }
    }
}