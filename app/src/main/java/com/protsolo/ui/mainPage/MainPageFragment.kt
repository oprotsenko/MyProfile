package com.protsolo.ui.mainPage

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.protsolo.database.ContactsDataFake
import com.protsolo.databinding.FragmentMainPageBinding
import com.protsolo.ui.baseFragment.BaseFragment
import com.protsolo.utils.Constants
import com.protsolo.utils.extensions.loadCircleImage

class MainPageFragment : BaseFragment<FragmentMainPageBinding>() {

    private val mainPageViewModel: MainPageViewModel by viewModels()

    override fun getViewBinding(): FragmentMainPageBinding =
        FragmentMainPageBinding.inflate(layoutInflater)

    override fun setUpViews() {
        val photo = binding.imageViewMainProfilePhoto
        photo.loadCircleImage(ContactsDataFake.getRandomImage())

        setName()
    }

    override fun setListeners() {
        binding.buttonMainViewContacts.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(Constants.FRAGMENT_BUNDLE_KEY, Constants.CONTACTS_FRAGMENT)
            navigator?.goToFragment(bundle)
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