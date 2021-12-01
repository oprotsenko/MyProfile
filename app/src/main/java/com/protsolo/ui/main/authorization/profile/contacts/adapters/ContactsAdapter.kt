package com.protsolo.ui.main.authorization.profile.contacts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.protsolo.R
import com.protsolo.databinding.ItemContactBinding
import com.protsolo.itemModel.UserModel
import com.protsolo.ui.main.authorization.profile.contacts.ContactsFragment


class ContactsAdapter(
    private val onItemClickListener: IItemClickListener,
    private val onItemChangedListener: IItemChangedListener
) :
    ListAdapter<UserModel, ContactsViewHolder>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactsViewHolder(ItemContactBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        with(holder) {
            bind(getItem(position))

            binding.apply {
                imageButtonDeleteContact.setOnClickListener {
                    onItemChangedListener.removeItem(position)
                }
                root.setOnLongClickListener {
                    onItemClickListener.onItemLongClick(position)
                    return@setOnLongClickListener true
                }
                if (ContactsFragment.isSelectionMood) {
                    root.setOnClickListener {
                        onItemClickListener.setUserSelected(position)
                    }
                } else {
                    root.setOnClickListener {
                        onItemClickListener.onItemClick(position, imageViewContact)
                    }
                }
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        val onItemDelete = { position: Int -> onItemChangedListener.removeItem(position) }
        ItemTouchHelper(SwipeToDelete(onItemDelete)).attachToRecyclerView(recyclerView)
    }

    override fun submitList(list: List<UserModel>?) {
        super.submitList(list?.let { ArrayList(it) })
    }
}