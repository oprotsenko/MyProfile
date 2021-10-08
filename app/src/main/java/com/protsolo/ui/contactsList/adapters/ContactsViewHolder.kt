package com.protsolo.ui.contactsList.adapters

import androidx.recyclerview.widget.RecyclerView
import com.protsolo.databinding.ContactListItemBinding
import com.protsolo.itemModel.UserModel
import com.protsolo.utils.loadCircleImageWithGlide

class ContactsViewHolder(private val binding: ContactListItemBinding)
    : RecyclerView.ViewHolder(binding.root) {

    private var contactName = binding.textViewContactsListContactName
    private var contactCareer = binding.textViewContactsListContactCareer
    private var image = binding.imageViewContact
    var deleteButton = binding.imageButtonDeleteContact

    fun bind(userModel: UserModel) {
        binding.apply {
            contactName.text = userModel.name
            contactCareer.text = userModel.career
            image.loadCircleImageWithGlide(userModel.image)
        }
    }
}