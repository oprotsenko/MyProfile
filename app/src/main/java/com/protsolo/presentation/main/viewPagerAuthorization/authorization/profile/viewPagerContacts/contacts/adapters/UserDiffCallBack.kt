package com.protsolo.presentation.main.viewPagerAuthorization.authorization.profile.viewPagerContacts.contacts.adapters

import androidx.recyclerview.widget.DiffUtil
import com.protsolo.app.item.WrapperUserModel

class UserDiffCallBack : DiffUtil.ItemCallback<WrapperUserModel>() {

    override fun areItemsTheSame(oldItem: WrapperUserModel, newItem: WrapperUserModel): Boolean {
        return oldItem.user.id == newItem.user.id
    }

    override fun areContentsTheSame(oldItem: WrapperUserModel, newItem: WrapperUserModel): Boolean {
        return oldItem == newItem
    }
}