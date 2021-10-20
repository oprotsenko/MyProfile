package com.protsolo.utils.extensions

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadCircleImageWithGlide(url: String) {
    Glide.with(this)
        .load(url)
        .circleCrop()
        .into(this)
}
fun ImageView.loadImageWithFresco(url: String) {
    setImageURI(Uri.parse(url))
}