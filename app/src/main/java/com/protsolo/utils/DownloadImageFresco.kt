package com.protsolo.utils

import android.net.Uri
import android.widget.ImageView

object DownloadImageFresco {

    fun setImage(photo: ImageView) {
        photo.setImageURI(Uri.parse(Constants.DEFAULT_IMAGE))
    }
}