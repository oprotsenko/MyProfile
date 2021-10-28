package com.protsolo.ui.contactsList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.protsolo.databinding.ActivityContactsListBinding
import com.protsolo.itemModel.UserModel
import com.protsolo.ui.AddContactDialogFragment
import com.protsolo.ui.INavigateToFragmentListener
import com.protsolo.ui.contactsList.adapters.ContactsAdapter
import com.protsolo.ui.contactsList.adapters.IContactListItemClickListener
import com.protsolo.ui.contactsList.adapters.decorations.ContactListItemDecoration
import com.protsolo.utils.Constants
import com.protsolo.utils.extensions.dpToPx

class ContactsListFragment : Fragment(), IContactListItemClickListener {

    val fragmentTag = Constants.CONTACTS_LIST

    private var listener: INavigateToFragmentListener? = null
    private val args: Bundle = Bundle()

    private lateinit var binding: ActivityContactsListBinding
    private lateinit var contactsViewModel: ContactsViewModel
    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var addContactDialogFragment: AddContactDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityContactsListBinding.inflate(layoutInflater)
        contactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)

        recyclerInit()
        setObserver()
        addContactDialogFragment = AddContactDialogFragment(onIContactListItemClickListener = this)
        setListeners()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is INavigateToFragmentListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun recyclerInit() {
        binding.recyclerViewContactsList.layoutManager =
            LinearLayoutManager(context)
        contactsAdapter = ContactsAdapter(onContactListItemClickListener = this)
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

    override fun onItemLongClick(position: Int) {
        args.putParcelable(
            Constants.BUNDLE_KEY,
            contactsViewModel.contactsData.value?.get(position)
        )

        listener?.onNavigateToFragment(Constants.DETAIL_VIEW, args)
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
        contactsViewModel.contactsData.observe(viewLifecycleOwner, {
            it?.let {
                contactsAdapter.submitList(it)
            }
        })
    }

    private fun setListeners() {
        binding.apply {
            textViewContactsListAddContact.setOnClickListener {
                addContactDialogFragment.show(
                    requireActivity().supportFragmentManager,
                    Constants.DIALOG_FRAGMENT_ADD_CONTACT_MESSAGE
                )
            }
            floatingActionButtonContactsListUp.setOnClickListener {
                recyclerViewContactsList.smoothScrollToPosition(0)
            }
            buttonContactsListBack.setOnClickListener {
                //todo
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(args: Bundle) =
            ContactsListFragment().apply {
                arguments = args
            }
    }
}
//package com.protsolo.ui.contactsList
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.protsolo.R
//
//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//
///**
// * A simple [Fragment] subclass.
// * Use the [ContactsListFragment.newInstance] factory method to
// * create an instance of this fragment.
// */
//class ContactsListFragment : Fragment() {
//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_contacts_list, container, false)
//    }
//
//}