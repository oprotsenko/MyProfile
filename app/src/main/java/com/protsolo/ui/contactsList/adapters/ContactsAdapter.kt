package com.protsolo.ui.contactsList.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.protsolo.R
import com.protsolo.databinding.ContactListItemBinding
import com.protsolo.itemModel.UserModel
import com.protsolo.utils.Constants


class ContactsAdapter(private val onContactListItemClickListener: IContactListItemClickListener)
    : ListAdapter<UserModel, ContactsViewHolder>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_list_item, parent, false)
        view.setOnClickListener {
            Snackbar.make(parent, Constants.LONG_PRESS_MESSAGE, Snackbar.LENGTH_LONG).show()
        }
        return ContactsViewHolder(ContactListItemBinding.bind(view), onContactListItemClickListener)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        with(holder) {
            bind(getItem(bindingAdapterPosition))
            deleteButton.setOnClickListener {
                deleteItem(bindingAdapterPosition)
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val onItemDelete = { position: Int ->
            deleteItem(position)
        }
        ItemTouchHelper(SwipeToDelete(onItemDelete)).attachToRecyclerView(recyclerView)
    }

    override fun submitList(list: MutableList<UserModel>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    private fun deleteItem(position: Int) {
        onContactListItemClickListener.removeItem(position)
    }
}