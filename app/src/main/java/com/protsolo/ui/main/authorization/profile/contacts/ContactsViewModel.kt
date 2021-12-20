package com.protsolo.ui.main.authorization.profile.contacts

import androidx.lifecycle.MutableLiveData
import com.protsolo.app.base.BaseViewModel
import com.protsolo.app.data.ContactsDataFake
import com.protsolo.app.item.UserModel
import com.protsolo.app.item.WrapperUserModel


class ContactsViewModel : BaseViewModel() {

    val contactsData by lazy { MutableLiveData<List<WrapperUserModel>>() }
    val isSelectionMode by lazy { MutableLiveData<Boolean>() }
    val selectedContacts: MutableList<Pair<Int, UserModel>> = mutableListOf()

    init {
        val list = ContactsDataFake.loadContacts()
        contactsData.value = list.map { user ->
            WrapperUserModel(user)
        }
        isSelectionMode.value = false
    }

    fun setSelectionMood(selectionMode: Boolean) {
        val list = contactsData.value?.map {
            it.copy(isSelectionMode = selectionMode)
        }
        contactsData.value = list
        isSelectionMode.value = selectionMode
    }

    fun removeItem(position: Int) {
        val list = contactsData.value?.toMutableList()
        list?.removeAt(position)
        contactsData.value = list
    }

    fun addItem(position: Int, userModel: UserModel) {
        val list = contactsData.value?.toMutableList()
        list?.add(position, WrapperUserModel(userModel))
        contactsData.value = list
    }

    fun setUserSelected(position: Int) {
        val list = contactsData.value?.toMutableList()
        val user = contactsData.value?.get(position)?.user
        if (contactsData.value?.get(position)?.isSelected == true) {
            list?.set(position, list[position].copy(isSelected = false))
            selectedContacts.remove(position to user)
        } else {
            list?.set(position, list[position].copy(isSelected = true))
            user?.let { selectedContacts.add(position to it) }
        }
        contactsData.value = list
        if (selectedContacts.isEmpty()) {
            setSelectionMood(false)
        }
    }

    fun deleteSelectedContacts() {
        val list = contactsData.value?.toMutableList()
        list?.removeIf {
            it.isSelected
        }
        contactsData.value = list
    }

    fun undoMultiRemove() {
        val list = contactsData.value?.toMutableList()
        selectedContacts.sortWith(compareBy { it.first })
        for (i in selectedContacts.indices) {
            list?.add(selectedContacts[i].first, WrapperUserModel(selectedContacts[i].second))
        }
        contactsData.value = list
    }
}