package com.protsolo.presentation.main.viewPager.authorization.profile.viewPager.contacts

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.protsolo.app.base.BaseViewModel
import com.protsolo.app.item.UserModel
import com.protsolo.app.item.WrapperUserModel
import com.protsolo.app.utils.SingleLiveEvent
import com.protsolo.data.remote.responses.ContactsResponse
import com.protsolo.domain.useCases.AddContactUseCase
import com.protsolo.domain.useCases.DeleteContactUseCase
import com.protsolo.domain.useCases.GetContactsUseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ContactsViewModel(
    private val getContactsUseCase: GetContactsUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val addContactUseCase: AddContactUseCase
    ) : BaseViewModel() {

    val contactsData by lazy { MutableLiveData<List<WrapperUserModel>>() }
    val isSelectionMood by lazy { MutableLiveData<Boolean>() }
    val selectedContacts: MutableList<Pair<Int, UserModel>> = mutableListOf()
    val responseMessage by lazy { SingleLiveEvent<String>() }

    init {
        fetchContacts()
    }

    fun fetchContacts() {
        try {
            val call = getContactsUseCase.getContacts()
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
                    responseMessage.value = t.message.orEmpty()
                }

            })
        } catch (e: Exception) {
            Log.d("fetchContacts", e.message.toString())
        }
    }

    fun setSelectionMood(selectionMood: Boolean) {
        isSelectionMood.value = selectionMood
    }

    fun removeItem(element: UserModel, position: Int) {
        try {
            val call = deleteContactUseCase.deleteContact(element.id)
            call.enqueue(object : Callback<ContactsResponse> {
                override fun onResponse(
                    call: Call<ContactsResponse>,
                    response: Response<ContactsResponse>
                ) {
                    if (response.isSuccessful) {
                        fetchContacts()
                    }
                }

                override fun onFailure(call: Call<ContactsResponse>, t: Throwable) {
                    responseMessage.value = t.message.orEmpty()
                }

            })
        } catch (e: Exception) {
            Log.d("removeContact", e.message.toString())
        }
    }

    fun addItem(contactId: Int) {
        addContactUseCase.addContact(contactId)
        fetchContacts()
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
        contactsData.value?.map {
            if (it.isSelected) {
                deleteContactUseCase.deleteContact(it.user.id)
            }
        }
        fetchContacts()
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