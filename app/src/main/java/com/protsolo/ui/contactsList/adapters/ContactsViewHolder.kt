package com.protsolo.ui.contactsList.adapters

import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.protsolo.R
import com.protsolo.databinding.ContactListItemBinding
import com.protsolo.itemModel.UserModel
import com.protsolo.utils.extensions.loadCircleImageWithGlide

class ContactsViewHolder(
    private val binding: ContactListItemBinding,
    private val contactListItemClickListener: IContactListItemClickListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    private var contactName = binding.textViewContactsListContactName
    private var contactCareer = binding.textViewContactsListContactCareer
    private var image = binding.imageViewContact
    var deleteButton = binding.imageButtonDeleteContact

    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
    }

    fun bind(userModel: UserModel) {
        binding.apply {
            contactName.text = userModel.name
            contactCareer.text = userModel.career
            image.loadCircleImageWithGlide(userModel.image)
        }
    }

    override fun onClick(v: View?) {
        contactListItemClickListener.onItemClick(bindingAdapterPosition)
    }

    override fun onLongClick(v: View?): Boolean {
        contactListItemClickListener.onItemLongClick(bindingAdapterPosition)
        return true
    }
}