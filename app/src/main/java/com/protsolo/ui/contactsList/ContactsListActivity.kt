package com.protsolo.ui.contactsList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.protsolo.databinding.ActivityContactsListBinding
import com.protsolo.itemModel.UserModel
import com.protsolo.ui.AddContactDialogFragment
import com.protsolo.ui.contactsList.adapters.ContactsAdapter
import com.protsolo.ui.contactsList.adapters.IContactListener
import com.protsolo.ui.contactsList.adapters.decorations.ContactListItemDecoration
import com.protsolo.utils.Constants
import com.protsolo.utils.extensions.dpToPx

class ContactsListActivity : AppCompatActivity(), IContactListener {

    private lateinit var binding: ActivityContactsListBinding
    private lateinit var contactsViewModel: ContactsViewModel
    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var addContactDialogFragment: AddContactDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)

        recyclerInit()
        setObserver()
        addContactDialogFragment = AddContactDialogFragment(onIContactListener = this)
        setListeners()
    }

    private fun recyclerInit() {
        binding.recyclerViewContactsList.layoutManager =
            LinearLayoutManager(this@ContactsListActivity)
        contactsAdapter = ContactsAdapter(onIContactListener = this)
        binding.recyclerViewContactsList.adapter = contactsAdapter
        addItemDecoration()
        setFabButton()
    }

    override fun removeItem(position: Int) {
        val element = contactsViewModel.contactsData.value?.get(position)
        contactsViewModel.removeItem(position)

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
    }

//    override fun showFloatButton() {
//        val showButton = AnimationUtils.loadAnimation(this, R.anim.fab_show)
//        binding.floatingActionButtonContactsListUp.startAnimation(showButton)
//    }
//
//    override fun hideFloatButton() {
//        val hideButton = AnimationUtils.loadAnimation(this, R.anim.fab_hide)
//        binding.floatingActionButtonContactsListUp.startAnimation(hideButton)
//    }

    private fun addItemDecoration() {
        binding.recyclerViewContactsList.addItemDecoration(
            ContactListItemDecoration(
                this.dpToPx(Constants.CONTACTS_ITEM_MARGIN)
            )
        )
    }

    private fun setFabButton() {
        with(binding) {
            recyclerViewContactsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val positionView =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    if (positionView > 0) {
                        if (!floatingActionButtonContactsListUp.isShown) {
                            floatingActionButtonContactsListUp.show()
                        }
                    } else {
                        if (floatingActionButtonContactsListUp.isShown) {
                            floatingActionButtonContactsListUp.hide()
                        }
                    }
                }
            })
        }
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
                addContactDialogFragment.show(
                    supportFragmentManager,
                    Constants.DIALOG_FRAGMENT_ADD_CONTACT_MESSAGE
                )
            }
            floatingActionButtonContactsListUp.setOnClickListener {
                recyclerViewContactsList.smoothScrollToPosition(0)
            }
            buttonContactsListBack.setOnClickListener {
                finish()
            }
        }
    }
}