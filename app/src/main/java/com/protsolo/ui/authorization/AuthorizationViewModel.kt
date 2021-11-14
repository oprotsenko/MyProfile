package com.protsolo.ui.authorization

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.protsolo.App
import com.protsolo.utils.Constants
import com.protsolo.utils.Constants.NAV_GRAPH

class AuthorizationViewModel : ViewModel() {

    private val preferenceStorage = App.preferencesStorage

    fun getEmail(arguments: Bundle?): String {
        var email = if (NAV_GRAPH) {
            arguments?.let { AuthorizationFragmentArgs.fromBundle(it).emailField }
        } else {
            arguments?.get(Constants.PREFERENCE_EMAIL_KEY)
        }
        if (email == null)
            email = ""
        return email as String
    }

    fun getPass(arguments: Bundle?): String {
        var pass = if (NAV_GRAPH) {
            arguments?.let { AuthorizationFragmentArgs.fromBundle(it).passField }
        } else {
            arguments?.get(Constants.PREFERENCE_PASSWORD_KEY)
        }
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