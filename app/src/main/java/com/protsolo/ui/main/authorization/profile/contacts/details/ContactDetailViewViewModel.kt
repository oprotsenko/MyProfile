package com.protsolo.ui.main.authorization.profile.contacts.details

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.protsolo.data.models.UserModel
import com.protsolo.app.utils.Constants

class ContactDetailViewViewModel: ViewModel() {

    val userData: MutableLiveData<UserModel> = MutableLiveData()

    fun extractArguments(arguments: Bundle?) {
        userData.value = if (Constants.NAV_GRAPH) {
            arguments?.let { ContactDetailViewFragmentArgs.fromBundle(it).userDetails }
        } else
            arguments?.get(Constants.USER_BUNDLE_KEY) as UserModel
    }
}