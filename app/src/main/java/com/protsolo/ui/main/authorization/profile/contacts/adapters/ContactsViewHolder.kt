package com.protsolo.ui.main.authorization.profile.contacts.adapters

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.protsolo.R
import com.protsolo.app.utils.extensions.loadCircleImage
import com.protsolo.databinding.ItemContactBinding
import com.protsolo.itemModel.UserModel
import com.protsolo.ui.main.authorization.profile.contacts.ContactsFragment
import com.protsolo.ui.main.authorization.profile.contacts.ContactsViewModel

class ContactsViewHolder(
    private val binding: ItemContactBinding,
    private val contactItemClickListener: IContactItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    val root = binding.root
    val deleteButton = binding.imageButtonDeleteContact

    fun bind(userModel: UserModel) {
        binding.apply {
            textViewContactsListContactName.text = userModel.name
            textViewContactsListContactCareer.text = userModel.career
            imageViewContact.loadCircleImage(userModel.image)
            imageViewContact.transitionName = userModel.image

            root.background = if (ContactsFragment.isSelectionMood) ContextCompat.getDrawable(
                root.context,
                R.drawable.item_selected_background
            ) else ContextCompat.getDrawable(root.context, R.drawable.item_frame_light_gray)

            imageViewItemContactIsSelected.visibility =
                if (ContactsFragment.isSelectionMood) View.VISIBLE else View.GONE
            imageViewItemContactIsSelected.background =
                if (ContactsViewModel.selectedContacts.contains(bindingAdapterPosition to userModel))
                    ContextCompat.getDrawable(root.context, R.drawable.ic_check_box_is_checked)
                else ContextCompat.getDrawable(root.context, R.drawable.ic_check_box_round)

            deleteButton.visibility =
                if (ContactsFragment.isSelectionMood) View.GONE else View.VISIBLE

            root.setOnClickListener {
                contactItemClickListener.onItemClick(bindingAdapterPosition, imageViewContact)
            }
            root.setOnLongClickListener {
                contactItemClickListener.onItemLongClick(bindingAdapterPosition)
                return@setOnLongClickListener true
            }
        }
    }
}