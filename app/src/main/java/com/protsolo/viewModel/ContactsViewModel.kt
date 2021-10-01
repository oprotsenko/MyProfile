package com.protsolo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.protsolo.itemModel.UserModel
import com.protsolo.utils.ContactsData

class ContactsViewModel : ViewModel() {

    var contactsData: MutableLiveData<MutableList<UserModel>> = MutableLiveData()

    init {
        contactsData.value = ContactsData.loadContacts()
    }

    fun removeItem(position: Int) {
        contactsData.value?.removeAt(position)
    }

    fun addItem(position: Int, userModel: UserModel) {
        contactsData.value?.add(position, userModel)
    }
}