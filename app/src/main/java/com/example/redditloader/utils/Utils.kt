package com.example.redditloader.utils

import android.view.View
import android.widget.ImageView
import com.example.imageloader.ImageLoader

fun ImageView.load(url: String) {
    ImageLoader.loadImage(this, url)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}