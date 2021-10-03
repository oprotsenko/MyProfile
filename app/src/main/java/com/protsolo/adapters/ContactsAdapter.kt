package com.protsolo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.protsolo.R
import com.protsolo.databinding.ContactListItemBinding
import com.protsolo.itemModel.UserModel


class ContactsAdapter(private val onIContactListener: IContactListener)
    : ListAdapter<UserModel, ContactsViewHolder>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_list_item, parent, false)
        return ContactsViewHolder(ContactListItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        with(holder) {
            bind(getItem(position))
            if (getItem(position).userId == getItem(itemCount - 1).userId) {
                onIContactListener.showFloatButton()
            } else {
                onIContactListener.hideFloatButton()
            }
            deleteButton.setOnClickListener {
                setItem(position)
            }
        }
    }

    override fun submitList(list: MutableList<UserModel>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    fun setItem(position: Int) {
        onIContactListener.removeItem(position)
    }

    fun addNewItem(element: UserModel, position: Int) {
        onIContactListener.addItem(element, position)
    }
}