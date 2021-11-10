package com.protsolo.ui.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.protsolo.database.ContactsDataFake
import com.protsolo.itemModel.UserModel
import com.protsolo.ui.contactDetailView.ContactDetailViewFragment
import com.protsolo.utils.Constants

class ContactsViewModel : ViewModel() {

    val contactsLiveData: MutableLiveData<MutableList<UserModel>> = MutableLiveData()

    init {
        contactsLiveData.value = ContactsDataFake.loadContacts()
    }

    fun removeItem(position: Int) {
        contactsLiveData.value?.removeAt(position)
        contactsLiveData.value = contactsLiveData.value
    }

    fun addItem(position: Int, userModel: UserModel) {
        contactsLiveData.value?.add(position, userModel)
        contactsLiveData.value = contactsLiveData.value
    }

    fun setUserSelected(position: Int) {
        val user = contactsLiveData.value?.get(position)
        val selected = user?.isSelected() as Boolean
        user.setUserSelected(!selected)
        if (user.isSelected()) {
            selectedContacts.add(user)
        } else {
            selectedContacts.remove(user)
        }
    }

    fun deleteSelectedContacts() {
        contactsLiveData.value?.removeAll(selectedContacts)
        contactsLiveData.value = contactsLiveData.value
        selectedContacts.clear()
    }

    fun getAction(position: Int): NavDirections =
        ContactsFragmentDirections.actionContactsFragmentToContactDetailViewFragment(
            contactsLiveData.value?.get(position)
        )

    fun getFragment(position: Int, args: Bundle): Fragment {
        args.putParcelable(
            Constants.BUNDLE_KEY,
            contactsLiveData.value?.get(position)
        )
        return ContactDetailViewFragment.newInstance(args)
    }

    companion object {
        val selectedContacts: MutableList<UserModel> = mutableListOf()
    }
}