package com.protsolo.ui.main.authorization.profile.contacts.adapters

import com.protsolo.itemModel.UserModel

interface IContactItemChangedListener {
    fun removeItem(position: Int)
    fun addItem(element: UserModel, position: Int)
    fun setUserSelected(position: Int)
}