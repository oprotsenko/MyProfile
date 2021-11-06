package com.protsolo.ui.authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
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

    fun getAction(email: String, pass: String): NavDirections =
        AuthorizationFragmentDirections.actionAuthorizationFragmentNavSelf(email, pass)

    fun getFragment(email: String, pass: String, args: Bundle): Fragment {
        args.putString(Constants.PREFERENCE_EMAIL_KEY, email)
        args.putString(Constants.PREFERENCE_PASSWORD_KEY, pass)
        return AuthorizationFragment.newInstance(args)
    }
}