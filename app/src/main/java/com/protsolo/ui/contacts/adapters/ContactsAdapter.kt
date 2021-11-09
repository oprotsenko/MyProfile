package com.protsolo.ui.contacts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.protsolo.R
import com.protsolo.databinding.ItemContactBinding
import com.protsolo.itemModel.UserModel


class ContactsAdapter(private val onContactItemClickListener: IContactItemClickListener) :
    ListAdapter<UserModel, ContactsViewHolder>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactsViewHolder(ItemContactBinding.bind(view), onContactItemClickListener)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        with(holder) {
            bind(getItem(bindingAdapterPosition))
            deleteButton.setOnClickListener {
                deleteItem(bindingAdapterPosition)
            }
//            checkBox.isChecked = getItem(bindingAdapterPosition).isSelected()
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        val onItemDelete = { position: Int -> deleteItem(position) }
        ItemTouchHelper(SwipeToDelete(onItemDelete)).attachToRecyclerView(recyclerView)
    }

    override fun submitList(list: MutableList<UserModel>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    private fun deleteItem(position: Int) {
        onContactItemClickListener.removeItem(position)
    }
}