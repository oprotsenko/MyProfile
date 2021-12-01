package com.protsolo.app.utils.extensions

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

object IntentUtils {
    fun isExistIntent(context: Context?, uri: Uri?) = context?.packageManager?.resolveActivity(
        Intent.getIntentOld(uri.toString()),
        PackageManager.MATCH_DEFAULT_ONLY
    ) != null
}