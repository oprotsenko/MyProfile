package com.protsolo.ui.contactsList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.protsolo.database.ContactsDataFake
import com.protsolo.itemModel.UserModel

class ContactsViewModel : ViewModel() {

    private val contactsData: MutableLiveData<MutableList<UserModel>> = MutableLiveData()

    init {
        contactsData.value = ContactsDataFake.loadContacts()
    }

    fun getData() = contactsData

    fun removeItem(position: Int) {
        contactsData.value?.removeAt(position)
        contactsData.value = contactsData.value
    }

    fun addItem(position: Int, userModel: UserModel) {
        contactsData.value?.add(position, userModel)
        contactsData.value = contactsData.value
    }
}