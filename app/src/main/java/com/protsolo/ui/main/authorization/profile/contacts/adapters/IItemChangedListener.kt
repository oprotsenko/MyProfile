package com.protsolo.ui.main.authorization.profile.contacts.adapters

import com.protsolo.app.item.UserModel

interface IItemChangedListener {
    fun removeItem(position: Int)
    fun addItem(element: UserModel, position: Int)
}