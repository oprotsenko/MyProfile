package com.protsolo.ui.main.authorization

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.protsolo.app.App
import com.protsolo.app.utils.Constants

class AuthorizationViewModel : ViewModel() {

    private val preferenceStorage = App.preferencesStorage

    fun getEmail(arguments: Bundle?): String {
        var email = arguments?.let { AuthorizationFragmentArgs.fromBundle(it).emailField }
        if (email == null)
            email = ""
        return email as String
    }

    fun getPass(arguments: Bundle?): String {
        var pass = arguments?.let { AuthorizationFragmentArgs.fromBundle(it).passField }
        if (pass == null)
            pass = ""
        return pass as String
    }

    fun isAutologin(): Boolean =
        if (preferenceStorage.getBoolean(Constants.PREFERENCE_AUTOLOGIN)
            && App.isFirstLogin
        ) {
            App.isFirstLogin = false
            true
        } else false

    fun writeToPreferenceStorage(email: String, pass: String, isAutologin: Boolean) {
        with(Constants) {
            preferenceStorage.save(
                PREFERENCE_EMAIL_KEY,
                email
            )
            preferenceStorage.save(
                PREFERENCE_PASSWORD_KEY,
                pass
            )
            preferenceStorage.save(
                PREFERENCE_AUTOLOGIN, isAutologin
            )
        }
    }

    fun getEmailFromPref(): String? = preferenceStorage.getString(Constants.PREFERENCE_EMAIL_KEY)
}