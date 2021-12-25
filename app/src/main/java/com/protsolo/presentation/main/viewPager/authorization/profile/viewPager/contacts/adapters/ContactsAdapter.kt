package com.protsolo.presentation.main.viewPager.authorization.profile.viewPager.contacts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.protsolo.R
import com.protsolo.app.item.WrapperUserModel
import com.protsolo.databinding.ItemContactBinding
import com.protsolo.presentation.main.viewPager.authorization.profile.viewPager.contacts.ContactsFragment
import com.protsolo.presentation.main.viewPager.authorization.profile.viewPager.users.adapters.ContactsViewHolder


class ContactsAdapter(
    private val onItemClickListener: IItemClickListener,
    private val onItemChangedListener: IItemChangedListener,
) :
    ListAdapter<WrapperUserModel, ContactsViewHolder>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactsViewHolder(ItemContactBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        with(holder) {
            bind(getItem(bindingAdapterPosition))

            binding.apply {
                imageButtonDeleteContact.setOnClickListener {
                    onItemChangedListener.removeItem(bindingAdapterPosition)
                }
                root.setOnLongClickListener {
                    onItemClickListener.onItemLongClick(bindingAdapterPosition)
                    return@setOnLongClickListener true
                }
                if (ContactsFragment.selectionView) {
                    root.setOnClickListener {
                        onItemClickListener.setUserSelected(bindingAdapterPosition)
                    }
                } else {
                    root.setOnClickListener {
                        onItemClickListener.onItemClick(bindingAdapterPosition, imageViewContact)
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

    override fun submitList(list: List<WrapperUserModel>?) {
        super.submitList(list?.let { ArrayList(it) })
    }
}