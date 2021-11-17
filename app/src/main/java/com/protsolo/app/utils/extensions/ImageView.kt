package com.protsolo.app.utils.extensions

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadCircleImage(url: String) {
    when (context) {
        is com.facebook.drawee.view.SimpleDraweeView -> setImageURI(Uri.parse(url))
        else -> Glide.with(this)
            .load(url)
            .circleCrop()
            .into(this)
    }
}