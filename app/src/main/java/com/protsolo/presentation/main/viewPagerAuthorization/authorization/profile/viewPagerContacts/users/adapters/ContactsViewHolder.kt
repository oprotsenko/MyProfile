package com.protsolo.presentation.main.viewPagerAuthorization.authorization.profile.viewPagerContacts.users.adapters

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.protsolo.R
import com.protsolo.app.item.WrapperUserModel
import com.protsolo.app.utils.extensions.loadCircleImage
import com.protsolo.databinding.ItemContactBinding

class ContactsViewHolder(
    val binding: ItemContactBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(wrapperUserModel: WrapperUserModel) {
        binding.apply {
            textViewContactsListContactName.text = wrapperUserModel.user.name
            textViewContactsListContactCareer.text = wrapperUserModel.user.career
            wrapperUserModel.user.image?.let { imageViewContact.loadCircleImage(it) }
            imageViewContact.transitionName = wrapperUserModel.user.id.toString()

//            root.background =
//                if (selectionView)
//                    ContextCompat.getDrawable(root.context, R.drawable.item_selected_background)
//                else ContextCompat.getDrawable(root.context, R.drawable.item_frame_light_gray)

//            imageViewItemContactIsSelected.visibility =
//                if (selectionView) View.VISIBLE
//                else View.GONE

//            imageViewItemContactIsSelected.background =
//                if (wrapperUserModel.isSelected)
//                    ContextCompat.getDrawable(root.context, R.drawable.ic_check_box_is_checked)
//                else ContextCompat.getDrawable(root.context, R.drawable.ic_check_box_round)

            imageButtonDeleteContact.visibility =
                if (wrapperUserModel.addContactView) View.GONE
                else View.VISIBLE

            buttonAddContact.visibility =
                if (wrapperUserModel.addContactView) View.VISIBLE
                else View.GONE

            if (wrapperUserModel.isAdded) {
                buttonAddContact.apply {
                    text = ""
                    setCompoundDrawablesRelative(null, null,
                    ContextCompat.getDrawable(root.context, R.drawable.ic_added_contact), null)
                }
            }
        }
    }
}