package com.protsolo.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.protsolo.R
import com.protsolo.databinding.ContactListItemBinding


class ContactsAdapter(private val contacts: List<UserModel>) :
    RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    class ContactsViewHolder(private val binding: ContactListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        var contactName = binding.textViewContactsListContactName
        var contactCareer = binding.textViewContactsListContactCareer

            fun bind(userModel: UserModel) {
                binding.apply {
                    contactName.text = userModel.contactName
                    contactCareer.text = userModel.contactCareer
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item, parent, false)
        return ContactsViewHolder(ContactListItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int = contacts.size
}