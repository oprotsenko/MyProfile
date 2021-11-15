package com.protsolo.ui.contacts.adapters

interface IContactItemClickListener {
    fun onItemLongClick(position: Int)
    fun onItemClick(position: Int)
}