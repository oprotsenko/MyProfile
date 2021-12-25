package com.protsolo.presentation.main.viewPager.authorization.profile.viewPager.contacts.adapters

import com.protsolo.app.item.UserModel

interface IItemChangedListener {
    fun removeItem(position: Int)
    fun addItem(element: UserModel)
}