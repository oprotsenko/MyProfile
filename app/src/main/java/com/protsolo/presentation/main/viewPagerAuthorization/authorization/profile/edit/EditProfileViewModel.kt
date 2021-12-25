package com.protsolo.presentation.main.viewPagerAuthorization.authorization.profile.edit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.protsolo.app.base.BaseViewModel
import com.protsolo.data.remote.requests.EditProfileRequest
import com.protsolo.data.remote.responses.ProfileResponse
import com.protsolo.domain.useCases.EditProfileUseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileViewModel(private val editProfileUseCase: EditProfileUseCase) : BaseViewModel() {

    val editResponse by lazy { MutableLiveData<Response<ProfileResponse>>() }

    fun editProfile(
        userName: String,
        career: String,
        address: String,
        phone: String,
        birthday: String,
        image: String
    ) {
        try {
            val call = editProfileUseCase.editProfile(
                EditProfileRequest(
                    userName,
                    phone,
                    address, career, image, birthday
                )
            )
            call.enqueue(object : Callback<ProfileResponse> {
                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    if (response.isSuccessful) {
                        editResponse.value = response
                    }
                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        } catch (e: Exception) {
            Log.d("EditProfile", e.message.toString())
        }
    }
}