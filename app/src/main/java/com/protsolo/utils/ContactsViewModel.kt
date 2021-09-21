package com.protsolo.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactsViewModel : ViewModel() {

    private var contactsData: MutableLiveData<List<UserModel>> = MutableLiveData()

    init {
        contactsData.value = ContactsData.loadContacts()
    }

    fun getContacts() = contactsData
}