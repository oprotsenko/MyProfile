package com.protsolo.presentation.main.viewPager.authorization.profile.viewPager.contacts.details

import androidx.lifecycle.MutableLiveData
import com.protsolo.app.base.BaseViewModel
import com.protsolo.app.item.UserModel

class ContactDetailViewViewModel : BaseViewModel() {
    val userData by lazy { MutableLiveData<UserModel>() }
}