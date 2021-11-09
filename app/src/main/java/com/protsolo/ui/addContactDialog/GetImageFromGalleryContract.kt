package com.protsolo.ui.addContactDialog

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts

class GetImageFromGalleryContract : ActivityResultContract<String, Uri?>() {

    override fun createIntent(context: Context, input: String): Intent {
        return Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = input
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? = when {
        resultCode != Activity.RESULT_OK -> null
        else -> {
            Intent.createChooser(intent, "Select image")
            intent?.data
        }
    }
}