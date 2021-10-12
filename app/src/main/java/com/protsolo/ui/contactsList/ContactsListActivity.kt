package com.protsolo.ui.contactsList

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.protsolo.R
import com.protsolo.ui.contactsList.adapters.ContactsAdapter
import com.protsolo.ui.contactsList.adapters.IContactListener
import com.protsolo.ui.contactsList.adapters.SwipeToDelete
import com.protsolo.ui.contactsList.adapters.decorations.ContactListItemDecoration
import com.protsolo.databinding.ActivityContactsListBinding
import com.protsolo.itemModel.UserModel
import com.protsolo.ui.AddContactFragment
import com.protsolo.ui.MainActivity
import com.protsolo.utils.Constants

class ContactsListActivity : AppCompatActivity(), IContactListener {

    private lateinit var binding: ActivityContactsListBinding
    private lateinit var contactsViewModel: ContactsViewModel
    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var addContactFragment: AddContactFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)
        binding.recyclerViewContactsList.layoutManager = LinearLayoutManager(this)
        contactsAdapter = ContactsAdapter(onIContactListener = this)
        binding.recyclerViewContactsList.adapter = contactsAdapter
        addContactFragment = AddContactFragment(onIContactListener = this)

        addItemDecoration()
        initSwipeToDelete()
        setObserver()
        setListeners()
    }

    override fun removeItem(position: Int) {
        val element = contactsViewModel.contactsData.value?.get(position)
        contactsViewModel.removeItem(position)
        contactsAdapter.submitList(contactsViewModel.contactsData.value)

        Snackbar.make(
            binding.recyclerViewContactsList, "${element?.name}" + Constants.SNACK_BAR_MESSAGE,
            Snackbar.LENGTH_LONG
        ).setAction(Constants.UNDO) {
            if (element != null) {
                addItem(element, position)
            }
        }.show()
    }

    override fun addItem(element: UserModel, position: Int) {
        contactsViewModel.addItem(position, element)
        contactsAdapter.submitList(contactsViewModel.contactsData.value)
    }

    override fun showFloatButton() {
        val showButton = AnimationUtils.loadAnimation(this, R.anim.fab_show)
        binding.floatingActionButtonContactsListUp.startAnimation(showButton)
    }

    override fun hideFloatButton() {
        val hideButton = AnimationUtils.loadAnimation(this, R.anim.fab_hide)
        binding.floatingActionButtonContactsListUp.startAnimation(hideButton)
    }

    private fun addItemDecoration() {
        val margin =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, Constants.CONTACTS_ITEM_MARGIN,
                resources.displayMetrics
            ).toInt()
        binding.recyclerViewContactsList.addItemDecoration(ContactListItemDecoration(margin))
    }

    private fun setObserver() {
        contactsViewModel.contactsData.observe(this, {
            it?.let {
                contactsAdapter.submitList(it)
            }
        })
    }

    private fun initSwipeToDelete() {
        val onItemDelete = { position: Int ->
            contactsAdapter.deleteItem(position)
        }
        ItemTouchHelper(SwipeToDelete(onItemDelete)).attachToRecyclerView(binding.recyclerViewContactsList)
    }

    private fun setListeners() {
        binding.apply {
            textViewContactsListAddContact.setOnClickListener {
                addContactFragment.show(
                    supportFragmentManager,
                    Constants.DIALOG_FRAGMENT_ADD_CONTACT_MESSAGE)
            }
            floatingActionButtonContactsListUp.setOnClickListener {
                recyclerViewContactsList.smoothScrollToPosition(0)
            }
            buttonContactsListBack.setOnClickListener {
                startActivity(Intent(baseContext, MainActivity::class.java))
                finish()
            }
        }
    }
}


