package com.protsolo.ui.main.authorization

import com.protsolo.app.architecture.BaseViewModel
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.SingleLiveEvent
import com.protsolo.app.utils.Validator
import com.protsolo.ui.main.MainActivity

class AuthorizationViewModel : BaseViewModel() {

    val isAutologin by lazy { SingleLiveEvent<Boolean>() }
    val enteredEmail by lazy { SingleLiveEvent<String>() }
    val enteredPass by lazy { SingleLiveEvent<String>() }
    val isValidEmail by lazy { SingleLiveEvent<Boolean>() }
    val isValidPass by lazy { SingleLiveEvent<Boolean>() }

    private val emailFromPref by lazy { SingleLiveEvent<String>() }

    init {
        isAutologin()
        getEmailFromPref()
        setEmail("")
        setPass("")
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

    fun isValidEmail() {
        isValidEmail.value = Validator.isValidEmail(enteredEmail.value)
    }

    fun isValidPass() {
        isValidPass.value = Validator.isValidPassword(enteredPass.value)
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