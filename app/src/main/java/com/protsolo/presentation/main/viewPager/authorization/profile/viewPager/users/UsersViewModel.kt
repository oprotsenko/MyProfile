package com.protsolo.presentation.main.viewPager.authorization.profile.viewPager.users

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.protsolo.app.base.BaseViewModel
import com.protsolo.app.item.WrapperUserModel
import com.protsolo.data.remote.responses.ContactsResponse
import com.protsolo.data.remote.IMyProfileApi
import com.protsolo.data.remote.responses.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UsersViewModel(private val myProfileApi: IMyProfileApi) : BaseViewModel() {

    val usersData by lazy { MutableLiveData<List<WrapperUserModel>>() }

    init {
        fetchAllUsers()
    }

    private fun fetchAllUsers() {
        try {
            val call = myProfileApi.getAllUsers()
            call.enqueue(object : Callback<UsersResponse> {
                override fun onResponse(
                    call: Call<UsersResponse>,
                    response: Response<UsersResponse>
                ) {
                    if (response.isSuccessful) {
                        usersData.value = response.body()?.data?.users?.map { user ->
                            WrapperUserModel(user, true)
                        }
                    }
                }

                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        } catch (e: Exception) {
            Log.d("fetchAllUsers", e.message.toString())
        }
    }

    fun addContact(id: Int, position: Int) {
        try {
            val call = myProfileApi.addContact(id)
            call.enqueue(object : Callback<ContactsResponse> {
                override fun onResponse(
                    call: Call<ContactsResponse>,
                    response: Response<ContactsResponse>
                ) {
                    if (response.isSuccessful) {
                        val list = usersData.value?.toMutableList()
                        list?.set(position, list[position].copy(isAdded = true))
                        usersData.value = list
                    }
                }

                override fun onFailure(call: Call<ContactsResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        } catch (e: Exception) {
            Log.d("adContact", e.message.toString())
        }
    }
}