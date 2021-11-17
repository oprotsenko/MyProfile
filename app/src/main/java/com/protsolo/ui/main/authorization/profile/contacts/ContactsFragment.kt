package com.protsolo.ui.main.authorization.profile.contacts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.protsolo.app.architecture.BaseFragment
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.extensions.dpToPx
import com.protsolo.databinding.FragmentContactsBinding
import com.protsolo.itemModel.UserModel
import com.protsolo.ui.main.authorization.profile.contacts.adapters.ContactsAdapter
import com.protsolo.ui.main.authorization.profile.contacts.adapters.IContactItemChangedListener
import com.protsolo.ui.main.authorization.profile.contacts.adapters.IContactItemClickListener
import com.protsolo.ui.main.authorization.profile.contacts.adapters.decorations.ContactListItemDecoration
import com.protsolo.ui.main.authorization.profile.contacts.dialog.AddContactDialogFragment

class ContactsFragment : BaseFragment<FragmentContactsBinding>(),
    IContactItemClickListener, IContactItemChangedListener {

    private val viewModelContacts: ContactsViewModel by viewModels()
    private val adapterContacts: ContactsAdapter by lazy {
        ContactsAdapter(
            onContactItemClickListener = this,
            onContactItemChangedListener = this
        )
    }

    override fun getViewBinding(): FragmentContactsBinding =
        FragmentContactsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerInit()
    }

    override fun setListeners() {
        binding.apply {
            textViewContactsAddContact.setOnClickListener {
                val addContactDialogFragment =
                    AddContactDialogFragment()
                addContactDialogFragment.show(
                    parentFragmentManager,
                    Constants.DIALOG_FRAGMENT_ADD_CONTACT_MESSAGE
                )
            }
            floatingButtonContactsUp.setOnClickListener {
                recyclerViewContacts.smoothScrollToPosition(0)
            }
            buttonContactsBack.setOnClickListener {
                navController.popBackStack()
            }
            floatingButtonContactsDelete.setOnClickListener {
                viewModelContacts.deleteSelectedContacts()
                isSelectingMood = false
                adapterContacts.notifyDataSetChanged()
                binding.apply {
                    floatingButtonContactsDelete.visibility = View.GONE
                    floatingButtonContactsUp.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun removeItem(position: Int) {
        val element = viewModelContacts.contactsData.value?.get(position)
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
        navController.navigate(
            ContactsFragmentDirections.actionContactsFragmentToContactDetailViewFragment(
                viewModelContacts.contactsData.value?.get(position)
            )
        )
    }

    override fun setUserSelected(position: Int) {
        viewModelContacts.setUserSelected(position)
    }

    override fun onItemLongClick(position: Int) {
        if (!isSelectingMood) {
            isSelectingMood = true
            adapterContacts.notifyDataSetChanged()
            binding.apply {
                floatingButtonContactsDelete.visibility = View.VISIBLE
                floatingButtonContactsUp.visibility = View.GONE
            }
        }
        viewModelContacts.setUserSelected(position)
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

    override fun setObserver() {
        viewModelContacts.contactsData.observe(viewLifecycleOwner, {
            it?.let {
                adapterContacts.submitList(it)
            }
        })

        parentFragmentManager.setFragmentResultListener(
            Constants.FRAGMENT_RESULT_LISTENER_KEY,
            viewLifecycleOwner, { _, bundle ->
                val fragmentResult = bundle.getParcelable<UserModel>(Constants.USER_BUNDLE_KEY)
                fragmentResult?.let { user -> addItem(user, 0) }
            })
    }

    companion object {
        var isSelectingMood = false
    }
}