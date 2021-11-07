package com.protsolo.ui.contactDetailView

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.protsolo.itemModel.UserModel
import com.protsolo.utils.Constants

class ContactDetailViewViewModel: ViewModel() {

    fun extractArguments(arguments: Bundle?): UserModel {
        val user = if (Constants.NAV_GRAPH) {
            arguments?.let { ContactDetailViewFragmentArgs.fromBundle(it).userDetails }
        } else
            arguments?.get(Constants.BUNDLE_KEY)
        return user as UserModel
    }
}