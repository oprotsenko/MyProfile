package com.protsolo.adapters

import androidx.recyclerview.widget.RecyclerView
import com.protsolo.databinding.ContactListItemBinding
import com.protsolo.model.UserModel
import com.protsolo.utils.loadCircleImageWithGlide

class ContactsViewHolder(private val binding: ContactListItemBinding)
    : RecyclerView.ViewHolder(binding.root) {

    val userDiffCallBack = UserDiffCallBack()

    var contactName = binding.textViewContactsListContactName
    var contactCareer = binding.textViewContactsListContactCareer
    var image = binding.imageViewContact
    var deleteButton = binding.imageButtonDeleteContact

    fun bind(userModel: UserModel) {
        binding.apply {
            contactName.text = userModel.name
            contactCareer.text = userModel.career
            image.loadCircleImageWithGlide(userModel.image)
        }
    }
}