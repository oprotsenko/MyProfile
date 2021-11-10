package com.protsolo.ui.contacts.adapters

import androidx.recyclerview.widget.DiffUtil
import com.protsolo.itemModel.UserModel

class ContactsListDiffCallBack(
    private val oldList: MutableList<UserModel>,
    private val newList: MutableList<UserModel>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].userId == newList[newItemPosition].userId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}