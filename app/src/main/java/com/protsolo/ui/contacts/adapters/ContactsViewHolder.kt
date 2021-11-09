package com.protsolo.ui.contacts.adapters

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.protsolo.R
import com.protsolo.databinding.ItemContactBinding
import com.protsolo.itemModel.UserModel
import com.protsolo.ui.contacts.ContactsFragment
import com.protsolo.utils.extensions.loadCircleImageWithGlide

class ContactsViewHolder(
    private val binding: ItemContactBinding,
    private val contactItemClickListener: IContactItemClickListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    val deleteButton = binding.imageButtonDeleteContact
    val checkBox = binding.checkBoxItemContactIsSelected

    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
    }

    fun bind(userModel: UserModel) {
        binding.apply {
            textViewContactsListContactName.text = userModel.name
            textViewContactsListContactCareer.text = userModel.career
            imageViewContact.loadCircleImageWithGlide(userModel.image)
            if (ContactsFragment.isSelectingMood) {
                root.background = ContextCompat.getDrawable(root.context, R.color.background)
                checkBoxItemContactIsSelected.visibility = View.VISIBLE
                deleteButton.visibility = View.INVISIBLE
                checkBoxItemContactIsSelected.isChecked = userModel.isSelected()
            }
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