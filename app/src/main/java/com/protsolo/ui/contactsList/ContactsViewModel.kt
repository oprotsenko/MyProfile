package com.protsolo.ui.contactsList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.protsolo.database.ContactsDataFake
import com.protsolo.itemModel.UserModel

class ContactsViewModel : ViewModel() {

    private val contactsLiveData: MutableLiveData<MutableList<UserModel>> = MutableLiveData()

    init {
        contactsLiveData.value = ContactsDataFake.loadContacts()
    }

    fun getData() = contactsLiveData

    fun removeItem(position: Int) {
        contactsLiveData.value?.removeAt(position)
        contactsLiveData.value = contactsLiveData.value
    }

    fun addItem(position: Int, userModel: UserModel) {
        contactsLiveData.value?.add(position, userModel)
        contactsLiveData.value = contactsLiveData.value
    }

    fun createObjectToShare(position: Int): String {
        val user = contactsLiveData.value?.get(position)
        return "Contact name: " + user?.name + "\n" +
                "phone: " + user?.phone + ".\nSent from MyProfile =)"
    }
}