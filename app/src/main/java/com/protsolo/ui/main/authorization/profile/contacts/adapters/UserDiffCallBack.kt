package com.protsolo.ui.main.authorization.profile.contacts.adapters

import androidx.recyclerview.widget.DiffUtil
import com.protsolo.itemModel.UserModel
import com.protsolo.ui.main.authorization.profile.contacts.ContactsViewModel

class UserDiffCallBack : DiffUtil.ItemCallback<UserModel>() {

    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return if (ContactsViewModel.user != null && newItem == ContactsViewModel.user) {
            ContactsViewModel.user = null
            false
        } else {
            oldItem == newItem
        }
    }
}