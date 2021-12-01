package com.protsolo.ui.main.authorization.profile.contacts.details

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.protsolo.R
import com.protsolo.app.architecture.BaseFragment
import com.protsolo.app.utils.extensions.loadCircleImage
import com.protsolo.databinding.FragmentProfileBinding

class ContactDetailViewFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {

    private val viewModel: ContactDetailViewViewModel by viewModels()

    override fun setAnimation() {
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpViews()
        setListeners()
        setObserver()
    }

    private fun setUpViews() {
        viewModel.userData.value =
            arguments?.let { ContactDetailViewFragmentArgs.fromBundle(it).userDetails }
        binding.apply {
            imageViewMainProfilePhoto.transitionName = viewModel.userData.value?.image
            textViewMainSettings.visibility = View.GONE
            buttonMainBack.visibility = View.VISIBLE
            textViewMainProfile.visibility = View.VISIBLE
            textViewMainSettingsDescription.visibility = View.INVISIBLE
            buttonMainEditProfile.visibility = View.INVISIBLE
            buttonMainViewContacts.text = resources.getText(R.string.contact_detail_view_message)
        }
    }

    private fun setObserver() {
        viewModel.userData.observe(viewLifecycleOwner, { user ->
            binding.apply {
                imageViewMainProfilePhoto.loadCircleImage(user.image)
                textViewMainUserName.text = user.name
                textViewMainCareer.text = user.career
                textViewMainHomeAddress.text = user.address
            }
        })
    }

    private fun setListeners() {
        binding.buttonMainBack.setOnClickListener {
            navigator.popBackStack()
        }
    }
}