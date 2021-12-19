package com.protsolo.ui.main.viewPager.authorization.signUp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.protsolo.app.base.BaseViewModel
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.PreferenceStorage
import com.protsolo.data.remote.IServiceApi
import com.protsolo.data.remote.RegisterResponse
import com.protsolo.data.remote.RegisterUserRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpViewModel(
    private val preferenceStorage: PreferenceStorage,
    private val serviceApi: IServiceApi
) : BaseViewModel() {
    lateinit var email: String
    lateinit var pass: String
    var userName: String? = null
    var phone: String? = null

    val isRegisteredState by lazy { MutableLiveData<Boolean>() }


    suspend fun registerUser() {
        try {
            val call = serviceApi.register(RegisterUserRequest(email, pass, userName, phone))
            call.enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        preferenceStorage.save(Constants.ACCESS_TOKEN, response.body()?.accessToken.toString())
                        preferenceStorage.save(Constants.REFRESH_TOKEN, response.body()?.refreshToken.toString())
                    }
                    isRegisteredState.value = response.isSuccessful
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    isRegisteredState.value = false
                }

            })
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
        }
    }

}