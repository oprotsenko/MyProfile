package com.protsolo.ui.addContactDialog

import androidx.lifecycle.ViewModel
import com.protsolo.itemModel.UserModel

class AddContactViewModel : ViewModel() {
    fun createUser(image: String, userName: String, career: String, address: String, phone: String): UserModel {
        return UserModel(0, image, userName, career, address, phone)
    }
}