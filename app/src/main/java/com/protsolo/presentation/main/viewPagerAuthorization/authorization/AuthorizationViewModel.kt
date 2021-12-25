package com.protsolo.presentation.main.viewPagerAuthorization.authorization

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.protsolo.app.base.BaseViewModel
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.PreferenceStorage
import com.protsolo.app.utils.SingleLiveEvent
import com.protsolo.app.utils.Validator
import com.protsolo.data.remote.requests.LoginRequest
import com.protsolo.data.remote.responses.AuthorizeResponse
import com.protsolo.domain.useCases.LoginUseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorizationViewModel(
    private val preferenceStorage: PreferenceStorage,
    private val validator: Validator,
    private val loginUseCase: LoginUseCase,
) : BaseViewModel() {

    val autologinState by lazy { SingleLiveEvent<Boolean>() }
    val proceedPermissionState by lazy { SingleLiveEvent<Boolean>() }
    val emailValidationState by lazy { MutableLiveData<Boolean>() }
    val passValidationState by lazy { MutableLiveData<Boolean>() }
    val responseState by lazy { SingleLiveEvent<Boolean>() }

    var isLoginPageView = true

    init {
        if (preferenceStorage.getBoolean(Constants.PREFERENCE_AUTOLOGIN)) {
            autologinState.value = true
        }
    }

    fun isLoginPage(isLoginPage: Boolean) {
        isLoginPageView = isLoginPage
    }

    fun setPermissionState(email: String, pass: String) {
        emailValidationState.value = validator.isValidEmail(email)
        passValidationState.value = validator.isValidPassword(pass)
        proceedPermissionState.value =
            (emailValidationState.value == true && passValidationState.value == true)
    }

    fun saveAutologinState(isAutologin: Boolean) {
        preferenceStorage.save(
            Constants.PREFERENCE_AUTOLOGIN, isAutologin
        )
    }

    fun loginRequest(email: String, pass: String) {
        try {
            val call = loginUseCase.login(LoginRequest(email, pass))
            call.enqueue(object : Callback<AuthorizeResponse> {
                override fun onResponse(
                    call: Call<AuthorizeResponse>,
                    response: Response<AuthorizeResponse>
                ) {
                    if (response.isSuccessful) {
                        preferenceStorage.save(
                            Constants.ACCESS_TOKEN,
                            response.body()?.data?.accessToken.toString()
                        )
                        preferenceStorage.save(
                            Constants.REFRESH_TOKEN,
                            response.body()?.data?.refreshToken.toString()
                        )
                    }
                    responseState.value = response.isSuccessful
                }

                override fun onFailure(call: Call<AuthorizeResponse>, t: Throwable) {
                    responseState.value = false
                }
            })
        } catch (e: Exception) {
            Log.d("LoginRequest", e.message.toString())
        }
    }
}
