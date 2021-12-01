package com.protsolo.ui.main.authorization.profile.contacts.details

import com.protsolo.app.architecture.BaseViewModel
import com.protsolo.app.utils.SingleLiveEvent
import com.protsolo.itemModel.UserModel

class ContactDetailViewViewModel : BaseViewModel() {
    val userData by lazy { SingleLiveEvent<UserModel>() }
}