package com.protsolo.ui.contacts.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.protsolo.databinding.ItemContactBinding
import com.protsolo.itemModel.UserModel
import com.protsolo.utils.extensions.loadCircleImage

class ContactsViewHolder(
    private val binding: ItemContactBinding,
    private val contactItemClickListener: IContactItemClickListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    var deleteButton = binding.imageButtonDeleteContact

    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
    }

    fun bind(userModel: UserModel) {
        binding.apply {
            textViewContactsListContactName.text = userModel.name
            textViewContactsListContactCareer.text = userModel.career
            imageViewContact.loadCircleImage(userModel.image)
        }
    }

    override fun onClick(v: View?) {
        contactItemClickListener.onItemClick(bindingAdapterPosition)
    }

    override fun onLongClick(v: View?): Boolean {
        contactItemClickListener.onItemLongClick(bindingAdapterPosition)
        return true
    }
}