package com.protsolo.presentation.main.viewPager.authorization.profile.viewPager.users

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.protsolo.R
import com.protsolo.app.base.BaseFragment
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.extensions.dpToPx
import com.protsolo.databinding.FragmentContactsBinding
import com.protsolo.presentation.main.viewPager.authorization.profile.viewPager.contacts.adapters.decorations.ContactListItemDecoration
import com.protsolo.presentation.main.viewPager.authorization.profile.viewPager.users.adapters.UsersAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : BaseFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate), IAddContactListener {

    private val viewModel: UsersViewModel by viewModel()
    private val adapterUsers: UsersAdapter by lazy {
        UsersAdapter(onIAddContactListener = this)
    }

    private var positionView = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpAddContactsView()
        recyclerInit()
        setObservers()
        setListeners()
    }

    private fun setUpAddContactsView() {
        binding.apply {
            textViewContactsContacts.text = getString(R.string.add_contact_users)
            textViewContactsAddContact.visibility = View.INVISIBLE
        }
    }

    private fun setListeners() {
        binding.apply {
            floatingButtonContactsUp.setOnClickListener {
                recyclerViewContacts.smoothScrollToPosition(0)
            }

            buttonContactsBack.setOnClickListener {
                navigator.popBackStack()
            }
        }
    }

    private fun recyclerInit() {
        binding.recyclerViewContacts.apply {
            adapter = adapterUsers
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
            usersData.observe(viewLifecycleOwner, {
                positionView =
                    (binding.recyclerViewContacts.layoutManager as LinearLayoutManager)
                        .findFirstVisibleItemPosition()
                it?.let {
                    adapterUsers.submitList(it)
                }
            })

            responseMessage.observe(viewLifecycleOwner, {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            })
        }
    }

    override fun addContact(id: Int, position: Int) {
        viewModel.addContact(id, position)
    }
}