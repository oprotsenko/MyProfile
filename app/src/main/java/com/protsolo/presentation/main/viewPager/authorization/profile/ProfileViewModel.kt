package com.protsolo.presentation.main.viewPager.authorization.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.protsolo.app.base.BaseViewModel
import com.protsolo.app.item.UserModel
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.NameParser
import com.protsolo.app.utils.PreferenceStorage
import com.protsolo.data.remote.IMyProfileApi
import com.protsolo.data.remote.responses.ProfileResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(
    preferenceStorage: PreferenceStorage,
    private val myProfileApi: IMyProfileApi,
    private val gson: Gson
) :
    BaseViewModel() {

    val userName by lazy { MutableLiveData<String>() }
    val user by lazy { MutableLiveData<UserModel>() }

    private val nameParser by lazy { NameParser() }

    init {
        viewModelScope.launch { getProfile() }
        userName.value =
            nameParser.parseName(preferenceStorage.getString(Constants.PREFERENCE_EMAIL_KEY))
    }

    private suspend fun getProfile() {
        try {
            val call = myProfileApi.getProfile()
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
                    TODO("Not yet implemented")
                }

            })
        } catch (e: Exception) {
            Log.d("GetProfile", e.message.toString())
        }
    }
}