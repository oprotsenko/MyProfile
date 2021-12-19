package com.protsolo.ui.main.viewPager.authorization.profile.contacts.adapters

import androidx.recyclerview.widget.DiffUtil
import com.protsolo.app.item.WrapperUserModel

class UserDiffCallBack : DiffUtil.ItemCallback<WrapperUserModel>() {

    override fun areItemsTheSame(oldItem: WrapperUserModel, newItem: WrapperUserModel): Boolean {
        return oldItem.user.userId == newItem.user.userId
    }

    override fun areContentsTheSame(oldItem: WrapperUserModel, newItem: WrapperUserModel): Boolean {
        return oldItem == newItem
    }
}