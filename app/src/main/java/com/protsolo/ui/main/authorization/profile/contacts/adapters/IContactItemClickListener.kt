package com.protsolo.ui.main.authorization.profile.contacts.adapters

import android.view.View

interface IContactItemClickListener {
    fun onItemLongClick(position: Int)
    fun onItemClick(position: Int, view: View)
    fun setUserSelected(position: Int)
}