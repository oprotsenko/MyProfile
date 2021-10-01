package com.protsolo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.TypedValue
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.protsolo.R
import com.protsolo.adapters.ContactsAdapter
import com.protsolo.adapters.IContactListener
import com.protsolo.adapters.SwipeToDelete
import com.protsolo.adapters.decorations.ContactListItemDecoration
import com.protsolo.databinding.ActivityContactsListBinding
import com.protsolo.itemModel.UserModel
import com.protsolo.utils.Constants
import com.protsolo.viewModel.ContactsViewModel

class ContactsListActivity : AppCompatActivity(), IContactListener {

    private lateinit var binding: ActivityContactsListBinding
    private lateinit var contactsListRecycleView: RecyclerView
    private lateinit var contactsViewModel: ContactsViewModel
    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var addContactFragment: AddContactFragment

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)
        contactsListRecycleView = binding.recyclerViewContactsList
        contactsListRecycleView.layoutManager = LinearLayoutManager(this)
        contactsAdapter = ContactsAdapter(onIContactClickListener = this)
        contactsListRecycleView.adapter = contactsAdapter

        addItemDecoration()
        setObserver()
        initSwipeToDelete()
        setListeners()

        addContactFragment = AddContactFragment(contactsAdapter)
//        addContactFragment = AddContactFragment()
    }

    private fun setObserver() {
        contactsViewModel.contactsData.observe(this, {
            it?.let {
                contactsAdapter.submitList(it)
            }
        })
    }

    private fun setListeners() {
        binding.apply {
            textViewContactsListAddContact.setOnClickListener {
                addContactFragment.show(supportFragmentManager, "add")
            }
            floatingActionButtonContactsListUp.setOnClickListener {
                contactsListRecycleView.smoothScrollToPosition(0)
            }
        }
    }

    override fun removeItem(position: Int) {
        val element = contactsViewModel.contactsData.value?.get(position)
        contactsViewModel.removeItem(position)
        setObserver()

        Snackbar.make(
            contactsListRecycleView, "${element?.name}" + Constants.SNACK_BAR_MESSAGE,
            5000
        ).setAction(Constants.UNDO) {
            if (element != null) {
                addItem(element, position)
            }
        }.show()
    }

    override fun addItem(element: UserModel, position: Int) {
        contactsViewModel.addItem(position, element)
        setObserver()
    }

    private fun initSwipeToDelete() {
        val onItemDelete = { position: Int ->
            contactsAdapter.setItem(position)
        }
        ItemTouchHelper(SwipeToDelete(onItemDelete)).attachToRecyclerView(contactsListRecycleView)
    }

    private fun addItemDecoration() {
        val margin =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, Constants.CONTACTS_ITEM_MARGIN,
                resources.displayMetrics
            ).toInt()
        contactsListRecycleView.addItemDecoration(ContactListItemDecoration(margin))
    }

    override fun showFloatButton() {
        val showButton = AnimationUtils.loadAnimation(this, R.anim.fab_show)
        binding.floatingActionButtonContactsListUp.startAnimation(showButton)
    }

    override fun hideFloatButton() {
        val hideButton = AnimationUtils.loadAnimation(this, R.anim.fab_hide)
        binding.floatingActionButtonContactsListUp.startAnimation(hideButton)
    }
}


