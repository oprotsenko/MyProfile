package com.protsolo.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.protsolo.R
import com.protsolo.databinding.ContactListItemBinding


class ContactsAdapter(private val contacts: List<UserModel>) :
    ListAdapter<UserModel, ContactsAdapter.ContactsViewHolder>(UserDiffCallBack()) {

    class ContactsViewHolder(private val binding: ContactListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        var contactName = binding.textViewContactsListContactName
        var contactCareer = binding.textViewContactsListContactCareer
        var image = binding.circleImageView

            fun bind(userModel: UserModel) {
                binding.apply {
                    contactName.text = userModel.contactName
                    contactCareer.text = userModel.contactCareer
                    Glide.with(image.context)
                        .load("https://scontent.fiev17-2.fna.fbcdn.net/v/t1.6435-9/242289197_4285719178131249_2304531192454725485_n.jpg?_nc_cat=101&ccb=1-5&_nc_sid=09cbfe&_nc_ohc=Oxz1lcqMvCkAX8NjPEn&_nc_ht=scontent.fiev17-2.fna&oh=b20254fb6d57b2202f29c63d329da38f&oe=616E2EA4")
                        .into(image)
                }
            }
    }

    class UserDiffCallBack : DiffUtil.ItemCallback<UserModel>() {
        override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem.equals(newItem)
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