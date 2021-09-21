package com.protsolo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.protsolo.databinding.ActivityContactsListBinding
import com.protsolo.utils.ContactsAdapter
import com.protsolo.utils.ContactsViewModel

class ContactsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactsListBinding
    private lateinit var contactsList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contactsList = binding.recyclerViewContactsList
        contactsList.layoutManager = LinearLayoutManager(this)

        val contactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)
        contactsViewModel.getContacts().observe(this, Observer {
            it?.let {
                contactsList.adapter = ContactsAdapter(it)
            }
        })
    }
}