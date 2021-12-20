package com.protsolo.ui.main.authorization

import androidx.lifecycle.MutableLiveData
import com.protsolo.app.base.BaseViewModel
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.PreferenceStorage
import com.protsolo.app.utils.SingleLiveEvent
import com.protsolo.app.utils.Validator

class AuthorizationViewModel(
    private val preferenceStorage: PreferenceStorage,
    private val validator: Validator
) : BaseViewModel() {

    val autologinState by lazy { SingleLiveEvent<Boolean>() }
    val registerPermissionState by lazy { SingleLiveEvent<Boolean>() }
    val emailValidationState by lazy { MutableLiveData<Boolean>() }
    val passValidationState by lazy { MutableLiveData<Boolean>() }
    var rememberMe = false

    var setUpLoginPageView = true

    init {
        isAutologin()
    }

    private fun isAutologin() {
        if (preferenceStorage.getBoolean(Constants.PREFERENCE_AUTOLOGIN)) {
            autologinState.value = true
        }
    }

    fun isLoginPage(isLoginPage: Boolean) {
        setUpLoginPageView = isLoginPage
    }

    fun register(email: String, pass: String) {
        emailValidationState.value = validator.isValidEmail(email)
        passValidationState.value = validator.isValidPassword(pass)
        val permissionAllowed =
            (emailValidationState.value == true && passValidationState.value == true)
        if (permissionAllowed) {
            writeToPreferenceStorage(email, pass, rememberMe)
        }
        registerPermissionState.value = permissionAllowed
    }

    private fun writeToPreferenceStorage(email: String, pass: String, isAutologin: Boolean) {
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