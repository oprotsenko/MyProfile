package com.protsolo.ui.contacts

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.protsolo.databinding.FragmentContactsBinding
import com.protsolo.itemModel.UserModel
import com.protsolo.ui.addContactDialog.AddContactDialogFragment
import com.protsolo.ui.baseFragment.BaseFragment
import com.protsolo.ui.contacts.adapters.ContactsAdapter
import com.protsolo.ui.contacts.adapters.IContactItemClickListener
import com.protsolo.ui.contacts.adapters.decorations.ContactListItemDecoration
import com.protsolo.utils.Constants
import com.protsolo.utils.extensions.dpToPx

class ContactsFragment : BaseFragment<FragmentContactsBinding>(),
    IContactItemClickListener {

    private val contactsViewModel: ContactsViewModel by viewModels()
    private val contactsAdapter: ContactsAdapter by lazy {
        ContactsAdapter(
            onContactItemClickListener = this
        )
    }

    override fun getViewBinding(): FragmentContactsBinding =
        FragmentContactsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerInit()
    }

    override fun setUpViews() {
        setObserver()
    }

    override fun setListeners() {
        binding.apply {
            textViewContactsListAddContact.setOnClickListener {
                val addContactDialogFragment =
                    AddContactDialogFragment(onIContactItemClickListener = this@ContactsFragment)
                addContactDialogFragment.show(
                    parentFragmentManager,
                    Constants.DIALOG_FRAGMENT_ADD_CONTACT_MESSAGE
                )
            }
            floatingActionButtonContactsListUp.setOnClickListener {
                recyclerViewContactsList.smoothScrollToPosition(0)
            }
            buttonContactsListBack.setOnClickListener {
                listener?.onBackButtonPressed()
            }
        }
    }

    override fun removeItem(position: Int) {
        val element = contactsViewModel.contactsLiveData.value?.get(position)
        contactsViewModel.removeItem(position)

        Snackbar.make(
            binding.root, "${element?.name}" + Constants.SNACK_BAR_MESSAGE,
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

    override fun onItemClick(position: Int) {
        if (Constants.NAV_GRAPH) {
            listener?.onNavigateTo(contactsViewModel.getAction(position))
        } else {
            listener?.onTransactionTo(contactsViewModel.getFragment(position, args))
        }
    }

    override fun onItemLongClick(position: Int) {
        contactsViewModel.getData().value?.get(position)?.setUserSelected(true)
    }

    private fun recyclerInit() {
        binding.recyclerViewContactsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactsAdapter
        }
        addItemDecoration()
        setFabButton()
    }

    private fun addItemDecoration() {
        binding.recyclerViewContactsList.addItemDecoration(
            ContactListItemDecoration(
                requireContext().dpToPx(Constants.CONTACTS_ITEM_MARGIN)
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
        contactsViewModel.contactsLiveData.observe(viewLifecycleOwner, {
            it?.let {
                contactsAdapter.submitList(it)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(args: Bundle) =
            ContactsFragment().apply {
                arguments = args
            }
    }
}