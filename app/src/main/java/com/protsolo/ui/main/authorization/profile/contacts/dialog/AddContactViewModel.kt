package com.protsolo.ui.main.authorization.profile.contacts.dialog

import androidx.lifecycle.MutableLiveData
import com.protsolo.app.architecture.BaseViewModel
import com.protsolo.itemModel.UserModel

class AddContactViewModel : BaseViewModel() {

    val userData: MutableLiveData<UserModel> = MutableLiveData()

    fun createUser(
        image: String,
        userName: String,
        career: String,
        address: String,
        phone: String
    ) {
        userData.value = UserModel(0, image, userName, career, address, phone)
    }
}