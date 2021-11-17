package com.protsolo.ui.main.authorization.profile.contacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.protsolo.data.ContactsDataFake
import com.protsolo.itemModel.UserModel

class ContactsViewModel : ViewModel() {

    val contactsData by lazy { MutableLiveData<MutableList<UserModel>>() }

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

    fun setUserSelected(position: Int) {
        val user = contactsData.value?.get(position)
        if (selectedContacts.contains(user)) {
            selectedContacts.remove(user)
        } else {
            user?.let { selectedContacts.add(it) }
        }
    }

    fun deleteSelectedContacts() {
        contactsData.value?.removeAll(selectedContacts)
        contactsData.value = contactsData.value
        selectedContacts.clear()
    }

    companion object {
        val selectedContacts: MutableList<UserModel> = mutableListOf()
    }
}