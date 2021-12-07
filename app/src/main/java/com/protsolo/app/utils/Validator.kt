package com.protsolo.app.utils

import android.util.Patterns

class Validator {

    fun isValidPassword(text: String?) =
        if (text.isNullOrEmpty()) false
        else Regex(Constants.PASSWORD_PATTERN).matches(text.toString())

    fun isValidEmail(text: String?) =
        if (text.isNullOrEmpty()) false
        else Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()
}