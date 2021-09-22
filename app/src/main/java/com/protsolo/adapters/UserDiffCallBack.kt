package com.protsolo.adapters

import androidx.recyclerview.widget.DiffUtil
import com.protsolo.model.UserModel

class UserDiffCallBack : DiffUtil.ItemCallback<UserModel>() {

    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem::class == newItem::class
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem::class == newItem::class
    }
}