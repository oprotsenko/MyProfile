package com.protsolo.ui

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.protsolo.adapters.ContactsAdapter
import com.protsolo.adapters.IContactClickListener
import com.protsolo.adapters.SwipeToDelete
import com.protsolo.adapters.decorations.ContactListItemDecoration
import com.protsolo.databinding.ActivityContactsListBinding
import com.protsolo.model.UserModel
import com.protsolo.utils.Constants
import com.protsolo.viewModel.ContactsViewModel

class ContactsListActivity : AppCompatActivity(), IContactClickListener {

    private lateinit var binding: ActivityContactsListBinding
    private lateinit var contactsListRecycleView: RecyclerView
    private lateinit var contactsViewModel: ContactsViewModel
    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var addContactFragment: AddContactFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contactsListRecycleView = binding.recyclerViewContactsList
        contactsListRecycleView.layoutManager = LinearLayoutManager(this)
        contactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)
        contactsAdapter = ContactsAdapter(contactsViewModel.contactsData.value!!, onIContactClickListener = this)

        val margin =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, Constants.CONTACTS_ITEM_MARGIN,
                resources.displayMetrics
            ).toInt()
        contactsViewModel.contactsData.observe(this, Observer {
            it?.let {
                contactsListRecycleView.addItemDecoration(ContactListItemDecoration(margin))
                contactsListRecycleView.adapter = contactsAdapter
                addContactFragment =
                    AddContactFragment(contactsAdapter)
            }
        })
//        addContactFragment = AddContactFragment()
        initSwipeToDelete()
        setListeners()
    }

    private fun setListeners() {
        binding.textViewContactsListAddContact.setOnClickListener {
            addContactFragment.show(supportFragmentManager, "add")
        }
    }

    override fun removeItem(position: Int) {
        contactsViewModel.removeItem(position)
    }

    override fun addItem(element: UserModel, position: Int) {
        contactsViewModel.addItem(position, element)
    }

    private fun initSwipeToDelete() {
        val onItemDelete = { position: Int ->
                contactsAdapter.setItem(position)
        }
        ItemTouchHelper(SwipeToDelete(onItemDelete)).attachToRecyclerView(contactsListRecycleView)
    }

}


