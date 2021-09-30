package com.protsolo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.google.android.material.snackbar.Snackbar
import com.protsolo.R
import com.protsolo.databinding.ContactListItemBinding
import com.protsolo.model.UserModel
import com.protsolo.utils.Constants


class ContactsAdapter(private val contacts: MutableList<UserModel>,
                      private val onIContactClickListener: IContactClickListener) :
    ListAdapter<UserModel, ContactsViewHolder>(UserDiffCallBack()) {

    private lateinit var viewGroup: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        viewGroup = parent
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_list_item, parent, false)
        return ContactsViewHolder(ContactListItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(contacts[position])
        setListeners(holder)
    }

    private fun setListeners(holder: ContactsViewHolder) {
        with(holder) {
            val position = bindingAdapterPosition
            deleteButton.setOnClickListener {
                setItem(position)
            }
        }
    }

    fun setItem(position: Int) : Unit {
        val element = contacts[position]
        onIContactClickListener.removeItem(position)
        notifyItemRemoved(position)

        Snackbar.make(viewGroup, "${element.name}" + Constants.SNACK_BAR_MESSAGE,
            5000).setAction(Constants.UNDO, View.OnClickListener {
            addItem(element, position)
            notifyItemInserted(position)
        }).show()
    }

    fun addItem(element: UserModel, position: Int) {
        onIContactClickListener.addItem(element, position)
    }

    override fun getItemCount(): Int = contacts.size
}