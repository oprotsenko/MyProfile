package com.protsolo.presentation.main.viewPagerAuthorization.authorization.profile.viewPagerContacts.contacts.adapters

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


class SwipeToDelete(private val onItemDelete: (Int) -> Unit)
    : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onItemDelete(viewHolder.bindingAdapterPosition)
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder) = 0.3f
}