package com.protsolo.ui.main.authorization.profile.contacts

import com.protsolo.app.architecture.BaseViewModel
import com.protsolo.app.utils.SingleLiveEvent
import com.protsolo.data.ContactsDataFake
import com.protsolo.itemModel.UserModel
import org.koin.core.component.KoinComponent


class ContactsViewModel : BaseViewModel(), KoinComponent {

    val contactsData by lazy { SingleLiveEvent<MutableList<UserModel>>() }
    val isSelectionMood by lazy { SingleLiveEvent<Boolean>() }
    val selectedContacts by lazy { SingleLiveEvent<MutableList<Pair<Int, UserModel>>>() }

    init {
        contactsData.value = ContactsDataFake.loadContacts()
        isSelectionMood.value = false
        selectedContacts.value = mutableListOf()
    }

    fun setSelectionMood(selectionMood: Boolean) {
        isSelectionMood.value = selectionMood
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
        if (selectedContacts.value?.contains(position to user) == true) {
            selectedContacts.value?.remove(position to user)
            if (selectedContacts.value?.isEmpty() == true) {
                setSelectionMood(false)
            }
        } else {
            user?.let { selectedContacts.value?.add(position to it) }
        }
        contactsData.value = contactsData.value
    }

    fun deleteSelectedContacts() {
        for (i in selectedContacts.value?.indices!!) {
            contactsData.value?.remove(selectedContacts.value?.get(i)?.second)
        }
        contactsData.value = contactsData.value
    }

    fun undoMultiRemove() {
        selectedContacts.value?.sortWith(compareBy { it.first })
        for (i in selectedContacts.value?.indices!!) {
            selectedContacts.value?.get(i)?.first?.let { position ->
                selectedContacts.value?.get(i)?.second?.let { user ->
                    contactsData.value?.add(position, user)
                }
            }
        }
        contactsData.value = contactsData.value
    }

    companion object {
        var user: UserModel? = null
        var selectedContactsCopy: MutableList<Pair<Int, UserModel>> = mutableListOf()
    }
}