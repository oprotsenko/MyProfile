package com.protsolo.ui.main.authorization

import androidx.lifecycle.ViewModel
import com.protsolo.app.App
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.SingleLiveEvent
import com.protsolo.app.utils.Validator
import com.protsolo.ui.main.MainActivity

class AuthorizationViewModel : ViewModel() {

    private val preferenceStorage = App.preferencesStorage

    val isAutologin by lazy { SingleLiveEvent<Boolean>() }
    val enteredEmail by lazy { SingleLiveEvent<String>() }
    val enteredPass by lazy { SingleLiveEvent<String>() }
    val isValidData by lazy { SingleLiveEvent<Boolean>() }

    private val emailFromPref by lazy { SingleLiveEvent<String>() }

    init {
        isAutologin()
        getEmailFromPref()
    }

    private fun isAutologin() {
        if (preferenceStorage.getBoolean(Constants.PREFERENCE_AUTOLOGIN)
            && MainActivity.isFirstLogin
        ) {
            MainActivity.isFirstLogin = false
            isAutologin.value = true
        }
    }

    private fun getEmailFromPref() {
        emailFromPref.value = preferenceStorage.getString(Constants.PREFERENCE_EMAIL_KEY)
    }

    fun setEmail(email: String) {
        enteredEmail.value = email
    }

    fun setPass(pass: String) {
        enteredPass.value = pass
    }

    fun isValid() {
        isValidData.value = Validator.isValidData(enteredEmail.value, enteredPass.value)
    }

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
}