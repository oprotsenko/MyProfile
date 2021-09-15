package com.protsolo.utils

import androidx.recyclerview.widget.DiffUtil

class UserDiffCallBack : DiffUtil.ItemCallback<UserModel>() {
    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.equals(newItem)
    }
}