package com.protsolo.ui.main.authorization

import androidx.lifecycle.MutableLiveData
import com.protsolo.app.base.BaseViewModel
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.PreferenceStorage
import com.protsolo.app.utils.SingleLiveEvent
import com.protsolo.app.utils.Validator

class AuthorizationViewModel(private val preferenceStorage: PreferenceStorage) : BaseViewModel() {

    val isLoginPage by lazy { MutableLiveData<Boolean>() }
    val isAutologin by lazy { SingleLiveEvent<Boolean>() }
    val enteredEmail by lazy { SingleLiveEvent<String>() }
    val enteredPass by lazy { SingleLiveEvent<String>() }
    val isValidEmail by lazy { SingleLiveEvent<Boolean>() }
    val isValidPass by lazy { SingleLiveEvent<Boolean>() }
    val isValidData by lazy { SingleLiveEvent<Boolean>() }

    private val emailFromPref by lazy { SingleLiveEvent<String>() }
    private val validator by lazy { Validator() }

    init {
        isAutologin()
        getEmailFromPref()
    }

    private fun isAutologin() {
        if (preferenceStorage.getBoolean(Constants.PREFERENCE_AUTOLOGIN)) {
            isAutologin.value = true
        }
    }

    private fun getEmailFromPref() {
        emailFromPref.value = preferenceStorage.getString(Constants.PREFERENCE_EMAIL_KEY)
    }

    fun isLoginPage(isLoginPage: Boolean) {
        this.isLoginPage.value = isLoginPage
    }

    fun setEnteredEmail(email: String) {
        enteredEmail.value = email
    }

    fun setEnteredPass(pass: String) {
        enteredPass.value = pass
    }

    fun isValidEmail() {
        isValidEmail.value = validator.isValidEmail(enteredEmail.value)
    }

    fun isValidPass() {
        isValidPass.value = validator.isValidPassword(enteredPass.value)
    }

    fun isValidData() {
        isValidData.value = (isValidEmail.value == true && isValidPass.value == true)
    }

    fun writeToPreferenceStorage(isAutologin: Boolean) {
        with(Constants) {
            preferenceStorage.save(
                PREFERENCE_EMAIL_KEY,
                enteredEmail.value.toString()
            )
            preferenceStorage.save(
                PREFERENCE_PASSWORD_KEY,
                enteredPass.value.toString()
            )
            preferenceStorage.save(
                PREFERENCE_AUTOLOGIN, isAutologin
            )
        }
    }
}