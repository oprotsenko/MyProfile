package com.protsolo.ui.contactDetailView

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.protsolo.R
import com.protsolo.databinding.FragmentMainPageBinding
import com.protsolo.ui.baseFragment.BaseFragment
import com.protsolo.utils.extensions.loadImageWithFresco

class ContactDetailViewFragment : BaseFragment<FragmentMainPageBinding>() {

    private val contactDetailViewViewModel: ContactDetailViewViewModel by viewModels()

    override fun getViewBinding(): FragmentMainPageBinding =
        FragmentMainPageBinding.inflate(layoutInflater)

    override fun setUpViews() {
        binding.apply {
            textViewMainSettings.visibility = View.GONE
            buttonMainBack.visibility = View.VISIBLE
            textViewMainProfile.visibility = View.VISIBLE
            textViewMainSettingsDescription.visibility = View.INVISIBLE
            buttonMainEditProfile.visibility = View.INVISIBLE
            buttonMainViewContacts.text = resources.getText(R.string.contact_detail_view_message)
        }

        val user = contactDetailViewViewModel.extractArguments(arguments)

        binding.apply {
            imageViewMainProfilePhoto.loadImageWithFresco(user.image)
            textViewMainUserName.text = user.name
            textViewMainCareer.text = user.career
            textViewMainHomeAddress.text = user.address
        }
    }

    override fun setListeners() {
        binding.buttonMainBack.setOnClickListener {
            listener?.onBackButtonPressed()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(args: Bundle) =
            ContactDetailViewFragment().apply {
                arguments = args
            }
    }
}