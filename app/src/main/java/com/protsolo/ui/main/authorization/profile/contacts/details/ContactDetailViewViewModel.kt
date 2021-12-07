package com.protsolo.ui.main.authorization.profile.contacts.details

import androidx.lifecycle.MutableLiveData
import com.protsolo.app.base.BaseViewModel
import com.protsolo.app.utils.SingleLiveEvent
import com.protsolo.app.item.UserModel

class ContactDetailViewViewModel : BaseViewModel() {
    val userData by lazy { MutableLiveData<UserModel>() }
}