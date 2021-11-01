package com.protsolo.utils

import android.util.Patterns

object Validator {

    fun isValidPassword(text: CharSequence?) =
        Regex(Constants.PASSWORD_PATTERN).matches(text.toString())

    fun isValidMail(text: CharSequence?) =
        Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()

    fun isValidData(email: CharSequence?, pass: CharSequence?) =
        isValidMail(email) && isValidPassword(pass)
}