package com.protsolo.ui.contactsList.adapters

import com.protsolo.itemModel.UserModel

interface IContactListener {
    fun removeItem(position: Int)
    fun addItem(element: UserModel, position: Int)
}