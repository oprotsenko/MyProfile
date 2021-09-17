package com.protsolo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.protsolo.databinding.ActivityContactsListBinding
import com.protsolo.utils.ContactsAdapter
import com.protsolo.utils.UserModel

class ContactsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactsListBinding
    private lateinit var contactsList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contactsList = binding.recyclerViewContactsList
        contactsList.layoutManager = LinearLayoutManager(this)

        contactsList.adapter = ContactsAdapter(fillList())
    }

    private fun fillList(): List<UserModel> {
        val list = mutableListOf<UserModel>()
        (0..30).forEach { i -> list.add(UserModel(null, "$i name", "$i career", "$i address")) }
        return list
    }
}