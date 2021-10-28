package com.protsolo.ui.contactsList.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.protsolo.databinding.ContactListItemBinding
import com.protsolo.itemModel.UserModel
import com.protsolo.utils.extensions.loadCircleImageWithGlide

class ContactsViewHolder(
    private val binding: ContactListItemBinding,
    private val contactListItemClickListener: IContactListItemClickListener
) : RecyclerView.ViewHolder(binding.root), View.OnLongClickListener {

    private var contactName = binding.textViewContactsListContactName
    private var contactCareer = binding.textViewContactsListContactCareer
    private var image = binding.imageViewContact
    var deleteButton = binding.imageButtonDeleteContact

    init {
        binding.root.setOnLongClickListener(this)
    }

    fun bind(userModel: UserModel) {
        binding.apply {
            contactName.text = userModel.name
            contactCareer.text = userModel.career
            image.loadCircleImageWithGlide(userModel.image)
        }
    }

    override fun onLongClick(v: View?): Boolean {
        contactListItemClickListener.onItemLongClick(bindingAdapterPosition)
        return true
    }
}