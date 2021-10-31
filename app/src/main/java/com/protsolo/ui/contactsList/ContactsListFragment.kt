package com.protsolo.ui.contactsList

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.protsolo.databinding.FragmentContactsListBinding
import com.protsolo.itemModel.UserModel
import com.protsolo.ui.addContactDialog.AddContactDialogFragment
import com.protsolo.ui.base.BaseFragment
import com.protsolo.ui.contactsList.adapters.ContactsAdapter
import com.protsolo.ui.contactsList.adapters.IContactListItemClickListener
import com.protsolo.ui.contactsList.adapters.decorations.ContactListItemDecoration
import com.protsolo.utils.Constants
import com.protsolo.utils.GlobalVal
import com.protsolo.utils.extensions.dpToPx

class ContactsListFragment : BaseFragment<FragmentContactsListBinding>(),
    IContactListItemClickListener {

    private val args: Bundle = Bundle()

    private lateinit var contactsViewModel: ContactsViewModel
    private lateinit var contactsAdapter: ContactsAdapter

    override fun getViewBinding(): FragmentContactsListBinding =
        FragmentContactsListBinding.inflate(layoutInflater)

    override fun setUpViews() {
        super.setUpViews()
        contactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)
        recyclerInit()
        setObserver()
    }

    override fun setListeners() {
        binding.apply {
            textViewContactsListAddContact.setOnClickListener {
                val addContactDialogFragment =
                    AddContactDialogFragment(onIContactListItemClickListener = this@ContactsListFragment)
                addContactDialogFragment.show(
                    requireActivity().supportFragmentManager,
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
        val element = contactsViewModel.getData().value?.get(position)
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

    override fun onItemClick(position: Int) {
        if (GlobalVal.NAV_GRAPH) {
            val action =
                ContactsListFragmentDirections.actionContactsListFragmentToContactDetailViewFragment(
                    contactsViewModel.getData().value?.get(position)
                )
            listener?.onNavigateToFragment(action)
        } else {
            args.putParcelable(
                Constants.BUNDLE_KEY,
                contactsViewModel.getData().value?.get(position)
            )
            listener?.onNavigateToFragment(Constants.DETAIL_VIEW, args)
        }
    }

    override fun onItemLongClick(position: Int) {
        val user = contactsViewModel.getData().value?.get(position)
        val contactToShare = "Contact name: " + user?.name + "\n" +
                "phone: " + user?.phone + ".\nSent from MyProfile =)"
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, contactToShare)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share contact:")
        startActivity(shareIntent)
    }

    private fun recyclerInit() {
        binding.recyclerViewContactsList.layoutManager = LinearLayoutManager(context)
        contactsAdapter = ContactsAdapter(onContactListItemClickListener = this)
        binding.recyclerViewContactsList.adapter = contactsAdapter

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
        contactsViewModel.getData().observe(viewLifecycleOwner, {
            it?.let {
                contactsAdapter.submitList(it)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(args: Bundle) =
            ContactsListFragment().apply {
                arguments = args
            }
    }
}