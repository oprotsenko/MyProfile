package com.protsolo.presentation.main.viewPager.authorization.profile.viewPager.contacts

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.protsolo.app.base.BaseViewModel
import com.protsolo.app.item.UserModel
import com.protsolo.app.item.WrapperUserModel
import com.protsolo.data.remote.responses.ContactsResponse
import com.protsolo.data.remote.IMyProfileApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ContactsViewModel(private val myProfileApi: IMyProfileApi) : BaseViewModel() {

    val contactsData by lazy { MutableLiveData<List<WrapperUserModel>>() }
    val isSelectionMood by lazy { MutableLiveData<Boolean>() }
    val selectedContacts: MutableList<Pair<Int, UserModel>> = mutableListOf()

    init {
        fetchContacts()
    }

    fun fetchContacts() {
        try {
            val call = myProfileApi.getContacts()
            call.enqueue(object : Callback<ContactsResponse> {
                override fun onResponse(
                    call: Call<ContactsResponse>,
                    response: Response<ContactsResponse>
                ) {
                    if (response.isSuccessful) {
                        contactsData.value = response.body()?.data?.contacts?.map { user ->
                            WrapperUserModel(user)
                        }
                        isSelectionMood.value = false
                    }
                }

                override fun onFailure(call: Call<ContactsResponse>, t: Throwable) {
                }

            })
        } catch (e: Exception) {
            Log.d("fetchContacts", e.message.toString())
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
                        val list = contactsData.value?.toMutableList()
                        list?.set(position, list[position].copy(isAdded = true))
                        contactsData.value = list
                    }
                }

                override fun onFailure(call: Call<ContactsResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        } catch (e: Exception) {
            Log.d("addContact", e.message.toString())
        }
    }

    fun setSelectionMood(selectionMood: Boolean) {
        isSelectionMood.value = selectionMood
    }

    fun removeItem(element: UserModel, position: Int) {
        try {
            val call = myProfileApi.deleteContact(element.id)
            call.enqueue(object : Callback<ContactsResponse> {
                override fun onResponse(
                    call: Call<ContactsResponse>,
                    response: Response<ContactsResponse>
                ) {
                    if (response.isSuccessful) {
                        val list = contactsData.value?.toMutableList()
        list?.removeAt(position)
        contactsData.value = list
                    }
                }

                override fun onFailure(call: Call<ContactsResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        } catch (e: Exception) {
            Log.d("removeContact", e.message.toString())
        }
//        val list = contactsData.value?.toMutableList()
//        list?.removeAt(position)
//        contactsData.value = list
    }

    fun addItem(userModel: UserModel) {
        val list = contactsData.value?.toMutableList()
        list?.add(WrapperUserModel(userModel))
        contactsData.value = list
    }

    fun setUserSelected(position: Int) {
        val list = contactsData.value?.toMutableList()
        val user = contactsData.value?.get(position)?.user
        if (contactsData.value?.get(position)?.isSelected == true) {
            list?.set(position, list[position].copy(isSelected = false))
            selectedContacts.remove(position to user)
            if (selectedContacts.isEmpty()) {
                setSelectionMood(false)
            }
        } else {
            list?.set(position, list[position].copy(isSelected = true))
            user?.let { selectedContacts.add(position to it) }
        }
        contactsData.value = list
    }

    fun deleteSelectedContacts() {
        val list = contactsData.value?.toMutableList()
        list?.removeIf {
            it.isSelected
        }
        contactsData.value = list
    }

    fun undoMultiRemove() {
        val list = contactsData.value?.toMutableList()
        selectedContacts.sortWith(compareBy { it.first })
        for (i in selectedContacts.indices) {
            list?.add(selectedContacts[i].first, WrapperUserModel(selectedContacts[i].second))
        }
        contactsData.value = list
    }
}