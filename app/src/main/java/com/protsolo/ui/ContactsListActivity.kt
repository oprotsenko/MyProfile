package com.protsolo.ui

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.protsolo.R
import com.protsolo.adapters.ContactsAdapter
import com.protsolo.adapters.SwipeToDelete
import com.protsolo.adapters.decorations.ContactListItemDecoration
import com.protsolo.databinding.ActivityContactsListBinding
import com.protsolo.utils.Constants
import com.protsolo.viewModel.ContactsViewModel


class ContactsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactsListBinding
    private lateinit var contactsList: RecyclerView
    private lateinit var addContactFragment: AddContactFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contactsList = binding.recyclerViewContactsList
        contactsList.layoutManager = LinearLayoutManager(this)

        val contactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)

        val margin =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Constants.CONTACTS_ITEM_MARGIN,
                resources.displayMetrics).toInt()
        contactsViewModel.getContacts().observe(this, Observer {
            it?.let {
                contactsList.addItemDecoration(ContactListItemDecoration(margin))
                contactsList.adapter = ContactsAdapter(it)
                ItemTouchHelper(SwipeToDelete(ContactsAdapter(it))).attachToRecyclerView(contactsList)
            }
        })
        addContactFragment = AddContactFragment()
        setListeners()
    }

    private fun setListeners() {
        binding.textViewContactsListAddContact.setOnClickListener {
            addContactFragment.show(supportFragmentManager, "add")
        }
    }
}