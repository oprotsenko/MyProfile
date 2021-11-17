package com.protsolo.ui.main.authorization.profile.contacts.adapters

import com.protsolo.data.models.UserModel

interface IContactItemChangedListener {
    fun removeItem(position: Int)
    fun addItem(element: UserModel, position: Int)
}