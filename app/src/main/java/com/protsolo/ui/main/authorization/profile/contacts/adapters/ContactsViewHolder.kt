package com.protsolo.ui.main.authorization.profile.contacts.adapters

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.protsolo.R
import com.protsolo.app.item.UserModel
import com.protsolo.app.utils.SelectionItemView
import com.protsolo.app.utils.extensions.loadCircleImage
import com.protsolo.databinding.ItemContactBinding
import com.protsolo.ui.main.authorization.profile.contacts.ContactsViewModel

class ContactsViewHolder(
    val binding: ItemContactBinding,
    private val selectionView: SelectionItemView
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(userModel: UserModel) {
        binding.apply {
            textViewContactsListContactName.text = userModel.name
            textViewContactsListContactCareer.text = userModel.career
            imageViewContact.loadCircleImage(userModel.image)
            imageViewContact.transitionName = userModel.image

            root.background =
                if (selectionView.isSelectionItemView)
                    ContextCompat.getDrawable(root.context, R.drawable.item_selected_background)
                else ContextCompat.getDrawable(root.context, R.drawable.item_frame_light_gray)

            imageViewItemContactIsSelected.visibility =
                if (selectionView.isSelectionItemView) View.VISIBLE
                else View.GONE

            imageViewItemContactIsSelected.background =
                if (ContactsViewModel.selectedContactsCopy.contains(bindingAdapterPosition to userModel))
                    ContextCompat.getDrawable(root.context, R.drawable.ic_check_box_is_checked)
                else ContextCompat.getDrawable(root.context, R.drawable.ic_check_box_round)

            imageButtonDeleteContact.visibility =
                if (selectionView.isSelectionItemView) View.GONE
                else View.VISIBLE
        }
    }
}