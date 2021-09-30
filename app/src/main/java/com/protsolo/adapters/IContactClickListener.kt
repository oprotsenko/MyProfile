package com.protsolo.adapters

import com.protsolo.model.UserModel

interface IContactClickListener {
    fun removeItem(position: Int)
    fun addItem(element: UserModel, position: Int)
}