package com.protsolo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.protsolo.databinding.ActivityContactsListBinding

class ContactsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactsListBinding
    private lateinit var contactsList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contactsList = binding.recyclerViewContactsList
        var layoutManager: LinearLayoutManager = LinearLayoutManager(this)
        contactsList.layoutManager = layoutManager
    }
}