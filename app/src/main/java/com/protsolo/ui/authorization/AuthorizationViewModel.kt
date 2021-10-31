package com.protsolo.ui.authorization

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.protsolo.utils.Constants

class AuthorizationViewModel : ViewModel() {

    fun isValidPassword(text: CharSequence?) =
        Regex(Constants.PASSWORD_PATTERN).matches(text.toString())

    fun isValidMail(text: CharSequence?) =
        Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()
}