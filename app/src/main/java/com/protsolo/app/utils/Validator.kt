package com.protsolo.app.utils

import android.util.Patterns

object Validator {

    fun isValidPassword(text: String?) =
        Regex(Constants.PASSWORD_PATTERN).matches(text.toString())

    fun isValidEmail(text: String?) =
        Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()
}