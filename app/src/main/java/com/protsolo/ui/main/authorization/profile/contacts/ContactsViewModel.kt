package com.protsolo.ui.main.authorization.profile.contacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.protsolo.data.ContactsDataFake
import com.protsolo.data.models.UserModel

class ContactsViewModel : ViewModel() {

    val contactsData: MutableLiveData<MutableList<UserModel>> = MutableLiveData()

    init {
        contactsData.value = ContactsDataFake.loadContacts()
    }

    fun removeItem(position: Int) {
        contactsData.value?.removeAt(position)
        contactsData.value = contactsData.value
    }

    fun addItem(position: Int, userModel: UserModel) {
        contactsData.value?.add(position, userModel)
        contactsData.value = contactsData.value
    }

    fun createObjectToShare(position: Int): String {
        val user = contactsData.value?.get(position)
        return "Contact name: " + user?.name + "\n" +
                "phone: " + user?.phone + ".\nSent from MyProfile =)"
    }
}