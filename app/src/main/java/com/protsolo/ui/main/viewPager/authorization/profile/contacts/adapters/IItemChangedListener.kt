package com.protsolo.ui.main.viewPager.authorization.profile.contacts.adapters

import com.protsolo.app.item.UserModel

interface IItemChangedListener {
    fun removeItem(position: Int)
    fun addItem(element: UserModel, position: Int)
}