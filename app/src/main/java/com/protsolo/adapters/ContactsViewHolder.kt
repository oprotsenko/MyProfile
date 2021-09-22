package com.protsolo.adapters

import androidx.recyclerview.widget.RecyclerView
import com.protsolo.databinding.ContactListItemBinding
import com.protsolo.model.UserModel
import com.protsolo.utils.RoundImageGlide

class ContactsViewHolder(private val binding: ContactListItemBinding)
    : RecyclerView.ViewHolder(binding.root) {

    var contactName = binding.textViewContactsListContactName
    var contactCareer = binding.textViewContactsListContactCareer
    var image = binding.imageViewContact

    fun bind(userModel: UserModel) {
        binding.apply {
            contactName.text = userModel.contactName
            contactCareer.text = userModel.contactCareer
            RoundImageGlide.makeRoundImage(image)
        }
    }
}