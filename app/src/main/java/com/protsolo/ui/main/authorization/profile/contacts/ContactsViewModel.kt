package com.protsolo.ui.main.authorization.profile.contacts

import androidx.lifecycle.ViewModel
import com.protsolo.app.utils.SingleLiveEvent
import com.protsolo.data.ContactsDataFake
import com.protsolo.itemModel.UserModel

class ContactsViewModel : ViewModel() {

    val contactsData by lazy { SingleLiveEvent<MutableList<UserModel>>() }
    val isSelectionMood by lazy { SingleLiveEvent<Boolean>() }

    init {
        contactsData.value = ContactsDataFake.loadContacts()
        isSelectionMood.value = ContactsFragment.isSelectionMood
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
        user = contactsData.value?.get(position)
        if (selectedContacts.contains(position to user)) {
            selectedContacts.remove(position to user)
        } else {
            user?.let { selectedContacts.add(position to it) }
        }
        contactsData.value = contactsData.value
    }

    fun deleteSelectedContacts() {
        for (i in selectedContacts.indices) {
            contactsData.value?.remove(selectedContacts[i].second)
        }
        contactsData.value = contactsData.value
    }

    fun addRemovedContactsList() {
        selectedContacts.sortWith(compareBy { it.first })
        for (i in selectedContacts.indices) {
            contactsData.value?.add(selectedContacts[i].first, selectedContacts[i].second)
        }
        contactsData.value = contactsData.value
    }

    companion object {
        var user: UserModel? = null
        val selectedContacts: MutableList<Pair<Int, UserModel>> = mutableListOf()
    }
}