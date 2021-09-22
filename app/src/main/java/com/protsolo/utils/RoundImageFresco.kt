package com.protsolo.utils

import android.net.Uri
import androidx.appcompat.widget.AppCompatImageView
import com.facebook.drawee.backends.pipeline.Fresco

object RoundImageFresco {

    fun makeRoundImage(photo: AppCompatImageView) {
        Fresco.initialize(photo.context)
        photo.setImageURI(Uri.parse(Constants.DEFAULT_IMAGE))
    }
}