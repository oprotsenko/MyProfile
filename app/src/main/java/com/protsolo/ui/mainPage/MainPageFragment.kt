package com.protsolo.ui.mainPage

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.protsolo.databinding.FragmentMainPageBinding
import com.protsolo.ui.baseFragment.BaseFragment
import com.protsolo.ui.contacts.ContactsFragment
import com.protsolo.utils.Constants
import com.protsolo.utils.extensions.loadImageWithFresco


class MainPageFragment : BaseFragment<FragmentMainPageBinding>() {

    private val mainPageViewModel: MainPageViewModel by viewModels()

    override fun getViewBinding(): FragmentMainPageBinding =
        FragmentMainPageBinding.inflate(layoutInflater)

    override fun setUpViews() {
        val photo = binding.imageViewMainProfilePhoto
        photo.loadImageWithFresco(Constants.DEFAULT_IMAGE)

        setName()
    }

    override fun setListeners() {
        binding.buttonMainViewContacts.setOnClickListener {
            if (Constants.NAV_GRAPH) {
                listener?.onNavigateTo(MainPageFragmentDirections.actionMainPageFragmentToContactsListFragment())
            } else {
                listener?.onTransactionTo(ContactsFragment.newInstance(args))
            }
        }
    }

    private fun setName() {
        val parsedUserName: String =
            mainPageViewModel.parseName(mainPageViewModel.getName(arguments))
        binding.textViewMainUserName.text = parsedUserName
    }

    companion object {
        fun newInstance(args: Bundle) =
            MainPageFragment().apply {
                arguments = args
            }
    }
}