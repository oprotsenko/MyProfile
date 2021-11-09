package com.protsolo.ui.contacts

import android.os.Bundle
import android.view.View
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

    private val viewModelContacts: ContactsViewModel by viewModels()
    private val adapterContacts: ContactsAdapter by lazy {
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
            floatingButtonContactsUp.setOnClickListener {
                recyclerViewContacts.smoothScrollToPosition(0)
            }
            buttonContactsListBack.setOnClickListener {
                listener?.onBackButtonPressed()
            }
        }
    }

    override fun removeItem(position: Int) {
        val element = viewModelContacts.contactsLiveData.value?.get(position)
        viewModelContacts.removeItem(position)

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
        viewModelContacts.addItem(position, element)
    }

    override fun onItemClick(position: Int) {
        if (isSelectingMood) {
            viewModelContacts.setUserSelected(position)
//            adapterContacts.notifyItemChanged(position)
        } else {
            if (Constants.NAV_GRAPH) {
                listener?.onNavigateTo(viewModelContacts.getAction(position))
            } else {
                listener?.onTransactionTo(viewModelContacts.getFragment(position, args))
            }
        }
    }

    override fun onItemLongClick(position: Int) {
        isSelectingMood = true
        binding.apply {
            floatingButtonContactsDelete.visibility = View.VISIBLE
            floatingButtonContactsUp.visibility = View.GONE
            recyclerViewContacts.recycledViewPool.clear()
        }
        viewModelContacts.setUserSelected(position)
        adapterContacts.notifyDataSetChanged()
    }

    private fun recyclerInit() {
        binding.recyclerViewContacts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterContacts
        }
        addItemDecoration()
        setFabButton()
    }

    private fun addItemDecoration() {
        binding.recyclerViewContacts.addItemDecoration(
            ContactListItemDecoration(
                requireContext().dpToPx(Constants.CONTACTS_ITEM_MARGIN)
            )
        )
    }

    private fun setFabButton() {
        with(binding) {
            recyclerViewContacts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val positionView =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    if (positionView > 0) {
                        if (!floatingButtonContactsUp.isShown) {
                            floatingButtonContactsUp.show()
                        }
                    } else {
                        if (floatingButtonContactsUp.isShown) {
                            floatingButtonContactsUp.hide()
                        }
                    }
                }
            })
        }
    }

    private fun setObserver() {
        viewModelContacts.contactsLiveData.observe(viewLifecycleOwner, {
            it?.let {
                adapterContacts.submitList(it)
            }
        })
    }

    companion object {
        fun newInstance(args: Bundle) =
            ContactsFragment().apply {
                arguments = args
            }

        var isSelectingMood = false
    }
}