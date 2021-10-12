package com.protsolo.ui.contactsList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.protsolo.itemModel.UserModel
import com.protsolo.database.ContactsDataFake

class ContactsViewModel : ViewModel() {

    val contactsData: MutableLiveData<MutableList<UserModel>> = MutableLiveData()

    init {
        contactsData.value = ContactsDataFake.loadContacts()
    }

    fun removeItem(position: Int) {
        contactsData.value?.removeAt(position)
    }

    fun addItem(position: Int, userModel: UserModel) {
        contactsData.value?.add(position, userModel)
    }
}