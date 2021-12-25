package com.protsolo.presentation.main.viewPager.authorization.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.protsolo.app.base.BaseViewModel
import com.protsolo.app.item.UserModel
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.PreferenceStorage
import com.protsolo.data.remote.responses.ProfileResponse
import com.protsolo.data.remote.responses.RefreshTokenResponse
import com.protsolo.domain.useCases.GetProfileUseCase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(
    private val preferenceStorage: PreferenceStorage,
    private val getProfileUseCase: GetProfileUseCase,
) :
    BaseViewModel() {

    val user by lazy { MutableLiveData<UserModel>() }

    init {
        viewModelScope.launch { getProfile() }
    }

    private suspend fun getProfile() {
        try {
            val call = getProfileUseCase.getProfile()
            call.enqueue(object : Callback<ProfileResponse> {
                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    if (response.isSuccessful) {
                        val userData = response.body()?.data
                        user.value = userData
                    }
                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    refreshToken()
                }

            })
        } catch (e: Exception) {
            Log.d("GetProfile", e.message.toString())
        }
    }

    private fun refreshToken() {
        try {
            val call = getProfileUseCase.refreshToken(
                "Bearer " + preferenceStorage.getString(Constants.REFRESH_TOKEN).orEmpty()
            )
            call.enqueue(object : Callback<RefreshTokenResponse> {
                override fun onResponse(
                    call: Call<RefreshTokenResponse>,
                    response: Response<RefreshTokenResponse>
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
                }

                override fun onFailure(call: Call<RefreshTokenResponse>, t: Throwable) {
                    Log.d("RefreshToken", t.message.toString())
                }

            })
        } catch (e: Exception) {
            Log.d("RefreshToken", e.message.toString())
        }
    }
}