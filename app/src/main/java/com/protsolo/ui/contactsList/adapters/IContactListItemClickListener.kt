package com.protsolo.ui.contactsList.adapters

import com.protsolo.itemModel.UserModel

interface IContactListItemClickListener {
    fun removeItem(position: Int)
    fun addItem(element: UserModel, position: Int)
    fun onItemLongClick(position: Int)
}