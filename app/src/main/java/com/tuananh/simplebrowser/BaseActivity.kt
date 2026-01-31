package com.tuananh.simplebrowser

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

abstract class BaseActivity: AppCompatActivity() {
    private val dialog by lazy {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.text_error))
            .setPositiveButton(getString(R.string.text_ok)) { dialog, _ -> dialog.dismiss() }
            .create()
    }

    private val progressBar by lazy {
        ProgressBar(this).apply {
            isIndeterminate = true
            isVisible = false
        }
    }
    private val params = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.WRAP_CONTENT,
        FrameLayout.LayoutParams.WRAP_CONTENT,
        Gravity.CENTER
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        addContentView(progressBar, params)
    }

    abstract fun initLayout()

    fun showProgressBar() {
        progressBar.isVisible = true
    }

    fun hideProgressBar() {
        progressBar.isVisible = false
    }

    fun showPopup(message: String, onDismiss: (() -> Unit)? = null) {
        dialog.setMessage(message)
        dialog.setOnDismissListener {
            onDismiss?.invoke()
        }
        if (!dialog.isShowing) {
            dialog.show()
        }
    }
}