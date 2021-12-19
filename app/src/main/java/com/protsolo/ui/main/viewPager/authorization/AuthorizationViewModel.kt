package com.protsolo.ui.main.viewPager.authorization

import androidx.lifecycle.MutableLiveData
import com.protsolo.app.base.BaseViewModel
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.PreferenceStorage
import com.protsolo.app.utils.SingleLiveEvent
import com.protsolo.app.utils.Validator
import com.protsolo.data.remote.IServiceApi
import com.protsolo.data.remote.LoginRequest
import com.protsolo.data.remote.LoginResponse
import retrofit2.Response

class AuthorizationViewModel(
    private val preferenceStorage: PreferenceStorage,
    private val validator: Validator,
    private val serviceApi: IServiceApi
) : BaseViewModel() {

    val autologinState by lazy { SingleLiveEvent<Boolean>() }
    val registerPermissionState by lazy { SingleLiveEvent<Boolean>() }
    val emailValidationState by lazy { MutableLiveData<Boolean>() }
    val passValidationState by lazy { MutableLiveData<Boolean>() }
    val response by lazy { SingleLiveEvent<Response<LoginResponse>>() }

    var isLoginPageView = true

    init {
        isAutologin()
    }

    private fun isAutologin() {
        if (preferenceStorage.getBoolean(Constants.PREFERENCE_AUTOLOGIN)) {
            autologinState.value = true
        }
    }

    fun isLoginPage(isLoginPage: Boolean) {
        isLoginPageView = isLoginPage
    }

    fun register(email: String, pass: String) {
        emailValidationState.value = validator.isValidEmail(email)
        passValidationState.value = validator.isValidPassword(pass)
        registerPermissionState.value =
            (emailValidationState.value == true && passValidationState.value == true)
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

    suspend fun loginRequest(email: String, pass: String) {
        response.value = serviceApi.login(LoginRequest(email, pass))
    }
}