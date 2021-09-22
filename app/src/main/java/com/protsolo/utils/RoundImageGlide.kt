package com.protsolo.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions

object RoundImageGlide {
    fun makeRoundImage(image: ImageView) {
        Glide.with(image.context).load(Constants.DEFAULT_IMAGE).
        apply(RequestOptions.bitmapTransform(CircleCrop())).into(image)
    }
}