package com.protsolo.ui.main.authorization.profile.contacts.adapters

import androidx.recyclerview.widget.DiffUtil
import com.protsolo.itemModel.UserModel

class UserDiffCallBack : DiffUtil.ItemCallback<UserModel>() {

    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem == newItem
    }
}