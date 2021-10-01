package com.protsolo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.protsolo.R
import com.protsolo.databinding.ContactListItemBinding
import com.protsolo.itemModel.UserModel
import kotlinx.coroutines.delay


class ContactsAdapter(private val onIContactClickListener: IContactListener) :
    ListAdapter<UserModel, ContactsViewHolder>(UserDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_list_item, parent, false)
        return ContactsViewHolder(ContactListItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        with(holder) {
            bind(getItem(position))
            if (getItem(position).userId == getItem(itemCount - 1).userId) {
                onIContactClickListener.showFloatButton()
            } else {
                onIContactClickListener.hideFloatButton()
            }
            deleteButton.setOnClickListener {
                setItem(position)
            }
        }
    }

    fun setItem(position: Int): Unit {
        onIContactClickListener.removeItem(position)
    }

//    private fun setListeners(holder: ContactsViewHolder) {
//        with(holder) {
//            val position = bindingAdapterPosition
//            deleteButton.setOnClickListener {
//                setItem(position)
//            }
//        }
//    }

//    fun setItem(position: Int) : Unit {
//        val element = contacts[position]
//        onIContactClickListener.removeItem(position)
//        this.submitList(contacts)
////        notifyItemRemoved(position)
//
//        Snackbar.make(viewGroup, "${element.name}" + Constants.SNACK_BAR_MESSAGE,
//            5000).setAction(Constants.UNDO, View.OnClickListener {
//            addItem(element, position)
//            this.submitList(contacts)
////            notifyItemInserted(position)
//        }).show()
//    }

//    fun addItem(element: UserModel, position: Int) {
//        onIContactClickListener.addItem(element, position)
//    }

//    override fun getItemCount(): Int = contacts.size
}