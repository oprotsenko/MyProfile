package com.protsolo.presentation.main.viewPager.authorization.profile.viewPager.users.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.protsolo.R
import com.protsolo.app.item.WrapperUserModel
import com.protsolo.databinding.ItemContactBinding
import com.protsolo.presentation.main.viewPager.authorization.profile.viewPager.contacts.adapters.UserDiffCallBack
import com.protsolo.presentation.main.viewPager.authorization.profile.viewPager.users.IAddContactListener


class UsersAdapter(private val onIAddContactListener: IAddContactListener) :
    ListAdapter<WrapperUserModel, ContactsViewHolder>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactsViewHolder(ItemContactBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        with(holder) {
            bind(getItem(bindingAdapterPosition))

            binding.apply {
                buttonAddContact.setOnClickListener {
                    onIAddContactListener.addContact(
                        getItem(bindingAdapterPosition).user.id,
                        bindingAdapterPosition
                    )
                }
//                root.setOnLongClickListener {
//                    onItemClickListener.onItemLongClick(bindingAdapterPosition)
//                    return@setOnLongClickListener true
//                }
            }
        }
    }

    override fun submitList(list: List<WrapperUserModel>?) {
        super.submitList(list?.let { ArrayList(it) })
    }
}