package com.protsolo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.protsolo.R
import com.protsolo.databinding.ContactListItemBinding
import com.protsolo.model.UserModel
import com.protsolo.utils.Constants


class ContactsAdapter(private val contacts: MutableList<UserModel>) :
    ListAdapter<UserModel, ContactsViewHolder>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_list_item, parent, false)
        return ContactsViewHolder(ContactListItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        with(holder) {
            bind(contacts[position])
            deleteButton.setOnClickListener {
                removeItem(holder)
            }
        }
    }

    private fun addItem(element: UserModel, position: Int) {
        contacts.add(position, element)
        notifyItemInserted(position)
    }

    fun removeItem(holder: RecyclerView.ViewHolder) {
        val position = holder.bindingAdapterPosition
        val element = contacts[position]
        contacts.removeAt(position)
        notifyItemRemoved(position)

        Snackbar.make(holder.itemView, "${element.name}" + Constants.SNACK_BAR_MESSAGE,
            5000).setAction(Constants.UNDO, View.OnClickListener {
            addItem(element, position)
        }).show()
    }

    override fun getItemCount(): Int = contacts.size
}