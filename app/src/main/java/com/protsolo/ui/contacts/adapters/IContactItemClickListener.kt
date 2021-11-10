package com.protsolo.ui.contacts.adapters

import com.protsolo.itemModel.UserModel

interface IContactItemClickListener {
    fun removeItem(position: Int)
    fun addItem(element: UserModel, position: Int)
    fun onItemLongClick(position: Int)
    fun onItemClick(position: Int)
    fun setUserSelected(position: Int)
    fun renewView()
}