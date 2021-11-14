package com.protsolo.ui.contacts.adapters

import com.protsolo.itemModel.UserModel

interface IContactItemClickListener {
    fun removeItem(position: Int)
    fun addItem(element: UserModel, position: Int): Unit
    fun onItemLongClick(position: Int)
    fun onItemClick(position: Int)
}