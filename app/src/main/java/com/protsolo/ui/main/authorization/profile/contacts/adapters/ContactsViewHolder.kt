package com.protsolo.ui.main.authorization.profile.contacts.adapters

import android.view.View
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.protsolo.databinding.ItemContactBinding
import com.protsolo.data.models.UserModel

class ContactsViewHolder(
    private val binding: ItemContactBinding,
    private val contactItemClickListener: IContactItemClickListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

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
        }
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