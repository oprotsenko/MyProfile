package com.protsolo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.protsolo.model.UserModel
import com.protsolo.utils.ContactsData

class ContactsViewModel : ViewModel() {

    private var contactsData: MutableLiveData<List<UserModel>> = MutableLiveData()

    init {
        contactsData.value = ContactsData.loadContacts()
    }

    fun getContacts() = contactsData
}