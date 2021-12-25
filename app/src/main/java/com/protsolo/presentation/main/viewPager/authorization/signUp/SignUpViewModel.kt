package com.protsolo.presentation.main.viewPager.authorization.signUp

import android.util.Log
import com.protsolo.app.base.BaseViewModel
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.PreferenceStorage
import com.protsolo.app.utils.SingleLiveEvent
import com.protsolo.data.remote.requests.RegisterUserRequest
import com.protsolo.data.remote.responses.AuthorizeResponse
import com.protsolo.domain.useCases.RegisterUseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpViewModel(
    private val preferenceStorage: PreferenceStorage,
    private val registerUseCase: RegisterUseCase
) : BaseViewModel() {

    val isRegisteredState by lazy { SingleLiveEvent<Boolean>() }
    var responseMessage = ""


    fun registerUser(email: String, pass: String, userName: String? = null, phone: String? = null) {
        try {
            val call = registerUseCase.register(
                RegisterUserRequest(email, pass, userName, phone)
            )
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
                    isRegisteredState.value = response.isSuccessful
                    responseMessage = response.message()
                }

                override fun onFailure(call: Call<AuthorizeResponse>, t: Throwable) {
                    isRegisteredState.value = false
                    responseMessage = t.message.orEmpty()
                }
            })
        } catch (e: Exception) {
            Log.d("RegisterUser", e.message.toString())
        }
    }
}