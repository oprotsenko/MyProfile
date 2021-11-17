package com.protsolo.ui.main.authorization.profile.contacts.details

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.protsolo.itemModel.UserModel

class ContactDetailViewViewModel: ViewModel() {

    val userData: MutableLiveData<UserModel> = MutableLiveData()

    fun extractArguments(arguments: Bundle?) {
        userData.value = arguments?.let { ContactDetailViewFragmentArgs.fromBundle(it).userDetails }
    }
}