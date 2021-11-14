package com.protsolo.ui.contactDetailView

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.protsolo.itemModel.UserModel
import com.protsolo.utils.Constants

class ContactDetailViewViewModel: ViewModel() {

    val userData: MutableLiveData<UserModel> = MutableLiveData()

    fun extractArguments(arguments: Bundle?) {
        userData.value = if (Constants.NAV_GRAPH) {
            arguments?.let { ContactDetailViewFragmentArgs.fromBundle(it).userDetails }
        } else
            arguments?.get(Constants.USER_BUNDLE_KEY) as UserModel
    }
}