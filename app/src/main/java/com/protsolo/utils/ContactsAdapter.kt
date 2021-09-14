package com.protsolo.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.protsolo.R
import com.protsolo.databinding.ContactListItemBinding


class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    var usersList: UsersList = UsersList()

    class ContactsViewHolder(private val binding: ContactListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bind(userModel: UserModel) {
                binding.apply {
                    textViewContactsListContactName.text = userModel.contactName
                    textViewContactsListContactCareer.text = userModel.contactCareer
                }
            }
    }

    var itemAmount = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val context: Context = parent.context
        val contactListItem = R.layout.contact_list_item
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view = inflater.inflate(contactListItem, parent, false)
        val binding: ContactListItemBinding = ContactListItemBinding.bind(view)
        val viewHolder = ContactsViewHolder(binding)
        itemAmount++
        return viewHolder
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(usersList.usersList[position])
    }

    override fun getItemCount(): Int {
        return itemAmount
    }
}