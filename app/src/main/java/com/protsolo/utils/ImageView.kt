package com.protsolo.utils

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

fun AppCompatImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .circleCrop()
        .into(this)


}