package com.protsolo.ui.mainPage

import android.os.Bundle
import android.view.View
import com.protsolo.databinding.FragmentMainPageBinding
import com.protsolo.ui.BaseFragment
import com.protsolo.utils.Constants
import com.protsolo.utils.extensions.loadImageWithFresco


class MainPageFragment : BaseFragment<FragmentMainPageBinding>() {

    private val args: Bundle = Bundle()

    override fun getViewBinding(): FragmentMainPageBinding =
        FragmentMainPageBinding.inflate(layoutInflater)

    override fun setUpViews() {
        super.setUpViews()
        val photo = binding.imageViewMainProfilePhoto
        photo.loadImageWithFresco(Constants.DEFAULT_IMAGE)

        setName()
    }

    override fun setListeners() {
        binding.buttonMainViewContacts.setOnClickListener {
            startContactsListActivity()
        }
    }

    /**
     * Gets the email from the intent message, if it is nothing there,
     * gets the email from the app base, calls the method to
     * parse it for the name and soname.
     */
    private fun setName() {
        var name = arguments?.getString(Constants.PREFERENCE_EMAIL_KEY)
        if (name.isNullOrEmpty()) {
            name = preferenceStorage.getString(Constants.PREFERENCE_EMAIL_KEY)
        }
        val parsedUserName: String = parseName(name)
        binding.textViewMainUserName.text = parsedUserName
    }

    /**
     * Parse the obtained string to the user name.
     */
    private fun parseName(name: String?): String {
        val res = StringBuilder()
        if (name.isNullOrEmpty())
            return Constants.DEFAULT_NAME
        for (c in name.indices) {
            when (name[c]) {
                '.' -> res.append(" ")
                '@' -> break
                else -> if (c == 0 || res[c - 1] == ' ') res.append(name[c].uppercase()) else
                    res.append(name[c])
            }
        }
        return res.toString()
    }

    private fun startContactsListActivity() {
        listener?.onNavigateToFragment(Constants.CONTACTS_LIST, args)
    }

    companion object {
        @JvmStatic
        fun newInstance(args: Bundle) =
            MainPageFragment().apply {
                arguments = args
            }
    }
}