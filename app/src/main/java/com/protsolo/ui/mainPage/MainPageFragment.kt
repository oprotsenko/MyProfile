package com.protsolo.ui.mainPage

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.protsolo.databinding.FragmentMainPageBinding
import com.protsolo.ui.base.BaseFragment
import com.protsolo.utils.Constants
import com.protsolo.utils.GlobalVal
import com.protsolo.utils.extensions.loadImageWithFresco


class MainPageFragment : BaseFragment<FragmentMainPageBinding>() {

    private val args: Bundle = Bundle()

    private lateinit var mainPageViewModel: MainPageViewModel

    override fun getViewBinding(): FragmentMainPageBinding =
        FragmentMainPageBinding.inflate(layoutInflater)

    override fun setUpViews() {
        super.setUpViews()
        mainPageViewModel = ViewModelProvider(this).get(MainPageViewModel::class.java)

        val photo = binding.imageViewMainProfilePhoto
        photo.loadImageWithFresco(Constants.DEFAULT_IMAGE)

        setName()
    }

    override fun setListeners() {
        binding.buttonMainViewContacts.setOnClickListener {
            if (GlobalVal.NAV_GRAPH) {
                listener?.onNavigateToFragment(MainPageFragmentDirections.actionMainPageFragmentToContactsListFragment())
            } else {
                listener?.onNavigateToFragment(Constants.CONTACTS_LIST, args)
            }
        }
    }

    /**
     * Gets the email from the intent message, if it is nothing there,
     * gets the email from the app base, calls the method to
     * parse it for the name and soname.
     */
    private fun setName() {
        var name = if (GlobalVal.NAV_GRAPH) {
            arguments?.let { MainPageFragmentArgs.fromBundle(it).email }
        } else {
            arguments?.getString(Constants.PREFERENCE_EMAIL_KEY)
        }
        if (name.isNullOrEmpty()) {
            name = preferenceStorage.getString(Constants.PREFERENCE_EMAIL_KEY)
        }
        val parsedUserName: String = mainPageViewModel.parseName(name)
        binding.textViewMainUserName.text = parsedUserName
    }

    companion object {
        @JvmStatic
        fun newInstance(args: Bundle) =
            MainPageFragment().apply {
                arguments = args
            }
    }
}