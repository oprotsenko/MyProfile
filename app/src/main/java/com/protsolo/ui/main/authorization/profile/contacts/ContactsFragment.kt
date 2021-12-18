package com.protsolo.ui.main.authorization.profile.contacts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.protsolo.app.base.BaseFragment
import com.protsolo.app.item.UserModel
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.extensions.dpToPx
import com.protsolo.databinding.FragmentContactsBinding
import com.protsolo.ui.main.authorization.profile.contacts.adapters.ContactsAdapter
import com.protsolo.ui.main.authorization.profile.contacts.adapters.IItemChangedListener
import com.protsolo.ui.main.authorization.profile.contacts.adapters.IItemClickListener
import com.protsolo.ui.main.authorization.profile.contacts.adapters.decorations.ContactListItemDecoration

class ContactsFragment : BaseFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate),
    IItemClickListener, IItemChangedListener {

    private val viewModel: ContactsViewModel by viewModels()
    private val adapterContacts: ContactsAdapter by lazy {
        ContactsAdapter(onItemClickListener = this, onItemChangedListener = this)
    }

    private var positionView = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerInit()
        setObservers()
        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            textViewContactsAddContact.setOnClickListener {
                navigator.navigate(
                    ContactsFragmentDirections
                        .actionContactsFragmentNavToAddContactDialogFragmentNav()
                )
            }

            floatingButtonContactsUp.setOnClickListener {
                recyclerViewContacts.smoothScrollToPosition(0)
            }

            buttonContactsBack.setOnClickListener {
                navigator.popBackStack()
            }

            floatingButtonContactsDelete.setOnClickListener {
                viewModel.deleteSelectedContacts()

                Snackbar.make(
                    binding.root, "${viewModel.selectedContacts.size}" +
                            Constants.SNACK_BAR_SELECTED_CONTACTS_REMOVED_MESSAGE,
                    Snackbar.LENGTH_LONG
                ).setAction(Constants.UNDO) {
                    viewModel.undoMultiRemove()
                }.show()

                viewModel.setSelectionMood(false)
            }
        }
    }

    override fun removeItem(position: Int) {
        val element = viewModel.contactsData.value?.get(position)
        viewModel.removeItem(position)

        Snackbar.make(
            binding.root, "${element?.user?.name}" + Constants.SNACK_BAR_ONE_CONTACT_REMOVED_MESSAGE,
            Snackbar.LENGTH_LONG
        ).setAction(Constants.UNDO) {
            if (element != null) {
                addItem(element.user, position)
            }
        }.show()
    }

    override fun addItem(element: UserModel, position: Int) {
        viewModel.addItem(position, element)
    }

    override fun onItemClick(position: Int, view: View) {
        val user = viewModel.contactsData.value?.get(position)
        val extras =
            FragmentNavigatorExtras((view to view.transitionName))
        user?.let {
            navigator.navigate(
                ContactsFragmentDirections
                    .actionContactsFragmentNavToContactDetailViewFragmentNav(it.user), extras
            )
        }
    }

    override fun onItemLongClick(position: Int) {
        viewModel.selectedContacts.clear()
        if (!selectionView) {
            viewModel.setSelectionMood(true)
        }
        setUserSelected(position)
    }

    override fun setUserSelected(position: Int) {
        viewModel.setUserSelected(position)
    }

    private fun recyclerInit() {
        binding.recyclerViewContacts.apply {
            adapter = adapterContacts
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
            itemAnimator?.changeDuration = 0
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

                    positionView =
                        (recyclerView.layoutManager as LinearLayoutManager)
                            .findFirstVisibleItemPosition()

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

    private fun setObservers() {
        viewModel.apply {
            contactsData.observe(viewLifecycleOwner, {
                positionView =
                    (binding.recyclerViewContacts.layoutManager as LinearLayoutManager)
                        .findFirstVisibleItemPosition()
                it?.let {
                    adapterContacts.submitList(it)
                }
            })

            isSelectionMood.observe(viewLifecycleOwner, {
                selectionView = it
                adapterContacts.notifyDataSetChanged()
                binding.apply {
                    floatingButtonContactsUp.visibility = if (it) View.GONE else View.VISIBLE
                    floatingButtonContactsDelete.visibility = if (it) View.VISIBLE else View.GONE
                }
            })
        }

        navigator.currentBackStackEntry?.savedStateHandle
            ?.getLiveData<UserModel>(Constants.USER_BUNDLE_KEY)
            ?.observe(viewLifecycleOwner) {
                addItem(it, 0)
            }
    }

    companion object {
        var selectionView = false
    }
}