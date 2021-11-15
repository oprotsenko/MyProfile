package com.protsolo.ui.contacts.adapters

import com.protsolo.itemModel.UserModel

interface IContactItemChangedListener {
    fun removeItem(position: Int)
    fun addItem(element: UserModel, position: Int)
}