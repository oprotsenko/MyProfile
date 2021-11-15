package com.protsolo.ui.contactDetailView

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.protsolo.R
import com.protsolo.databinding.FragmentMainPageBinding
import com.protsolo.ui.baseFragment.BaseFragment
import com.protsolo.ui.contacts.adapters.ContactsViewHolder
import com.protsolo.utils.extensions.loadCircleImage

class ContactDetailViewFragment : BaseFragment<FragmentMainPageBinding>() {

    private val viewModelContactDetailView: ContactDetailViewViewModel by viewModels()

    override fun getViewBinding(): FragmentMainPageBinding =
        FragmentMainPageBinding.inflate(layoutInflater)

    override fun setAnimation() {
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun setUpViews() {
        binding.apply {
            imageViewMainProfilePhoto.transitionName = ContactsViewHolder.transitionNameImage
            textViewMainSettings.visibility = View.GONE
            buttonMainBack.visibility = View.VISIBLE
            textViewMainProfile.visibility = View.VISIBLE
            textViewMainSettingsDescription.visibility = View.INVISIBLE
            buttonMainEditProfile.visibility = View.INVISIBLE
            buttonMainViewContacts.text = resources.getText(R.string.contact_detail_view_message)
        }

        viewModelContactDetailView.extractArguments(arguments)
    }

    override fun setObserver() {
        viewModelContactDetailView.userData.observe(viewLifecycleOwner, { user ->
            binding.apply {
                imageViewMainProfilePhoto.loadCircleImage(user.image)
                textViewMainUserName.text = user.name
                textViewMainCareer.text = user.career
                textViewMainHomeAddress.text = user.address
            }
        })
    }

    override fun setListeners() {
        binding.buttonMainBack.setOnClickListener {
            navigator?.onBackButtonPressed()
        }
    }

    companion object {
        fun newInstance(args: Bundle) =
            ContactDetailViewFragment().apply {
                arguments = args
            }
    }
}