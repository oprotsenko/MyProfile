package com.protsolo.ui.main.authorization.profile.contacts.dialog.contracts

import android.Manifest
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class RequestPermission : ActivityResultContract<String, Boolean>() {

    private val REQUEST_CODE = 1
    private val permission = Manifest.permission.READ_EXTERNAL_STORAGE

    override fun createIntent(context: Context, input: String?): Intent {
        TODO("Not yet implemented")
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        TODO("Not yet implemented")
    }
}
