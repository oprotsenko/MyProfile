package com.protsolo.ui.main.authorization.profile.contacts.details

import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.protsolo.R
import com.protsolo.app.architecture.BaseFragment
import com.protsolo.app.utils.extensions.loadCircleImage
import com.protsolo.databinding.FragmentProfileBinding
import com.protsolo.ui.main.authorization.profile.contacts.adapters.ContactsViewHolder

class ContactDetailViewFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModelContactDetailView: ContactDetailViewViewModel by viewModels()

    override fun getViewBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)

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
            navController.popBackStack()
        }
    }
}