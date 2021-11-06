package com.protsolo.ui.addContactDialog

import androidx.lifecycle.ViewModel
import com.protsolo.database.ContactsDataFake
import com.protsolo.itemModel.UserModel

class AddContactViewModel : ViewModel() {
    fun createUser(userName: String, career: String, address: String, phone: String): UserModel {
        return UserModel(0, ContactsDataFake.getRandomImage(), userName, career, address, phone)
    }
}