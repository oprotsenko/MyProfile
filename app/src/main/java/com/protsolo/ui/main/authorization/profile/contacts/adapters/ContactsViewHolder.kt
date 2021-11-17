package com.protsolo.ui.main.authorization.profile.contacts.adapters

import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.protsolo.R
import com.protsolo.app.utils.extensions.loadCircleImage
import com.protsolo.itemModel.UserModel
import com.protsolo.databinding.ItemContactBinding
import com.protsolo.ui.main.authorization.profile.contacts.ContactsFragment
import com.protsolo.ui.main.authorization.profile.contacts.ContactsViewModel

class ContactsViewHolder(
    private val binding: ItemContactBinding,
    private val contactItemClickListener: IContactItemClickListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    val root = binding.root
    val deleteButton = binding.imageButtonDeleteContact

    lateinit var user: UserModel

    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
    }

    fun bind(userModel: UserModel) {
        user = userModel
        binding.apply {
            textViewContactsListContactName.text = userModel.name
            textViewContactsListContactCareer.text = userModel.career
            imageViewContact.loadCircleImage(userModel.image)
            imageViewContact.transitionName = userModel.image

            root.background = if (ContactsFragment.isSelectingMood) ContextCompat.getDrawable(
                root.context,
                R.drawable.item_selected_background
            ) else ContextCompat.getDrawable(root.context, R.drawable.item_frame_light_gray)

            imageViewItemContactIsSelected.visibility =
                if (ContactsFragment.isSelectingMood) View.VISIBLE else View.GONE
            imageViewItemContactIsSelected.background =
                if (ContactsViewModel.selectedContacts.contains(userModel)) ContextCompat.getDrawable(root.context,
                    R.drawable.ic_check_box_is_checked)
                else ContextCompat.getDrawable(root.context, R.drawable.ic_check_box_round) }

        deleteButton.visibility =
            if (ContactsFragment.isSelectingMood) View.GONE else View.VISIBLE
    }

    override fun onClick(v: View?) {
        transitionNameImage = user.image
        extras = FragmentNavigatorExtras(binding.imageViewContact to transitionNameImage)
        contactItemClickListener.onItemClick(bindingAdapterPosition)
    }

    override fun onLongClick(v: View?): Boolean {
        contactItemClickListener.onItemLongClick(bindingAdapterPosition)
        return true
    }

    companion object {
        var transitionNameImage = ""
        lateinit var extras: FragmentNavigator.Extras
    }
}