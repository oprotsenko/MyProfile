package com.protsolo.ui.main.authorization.profile.contacts.details

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.protsolo.app.utils.SingleLiveEvent
import com.protsolo.itemModel.UserModel

class ContactDetailViewViewModel: ViewModel() {

    val userData by lazy { SingleLiveEvent<UserModel>() }

    fun extractArguments(arguments: Bundle?) {
        userData.value = arguments?.let { ContactDetailViewFragmentArgs.fromBundle(it).userDetails }
    }
}