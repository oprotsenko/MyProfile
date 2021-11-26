package com.protsolo.ui.main.authorization.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.protsolo.app.architecture.BaseFragment
import com.protsolo.app.utils.extensions.loadCircleImage
import com.protsolo.data.ContactsDataFake
import com.protsolo.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun getViewBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObservers()
        setUpViews()
        setListeners()
    }

    private fun setObservers() {
        viewModel.userName.observe(viewLifecycleOwner, { userName ->
            binding.textViewMainUserName.text = userName
        })
    }

    private fun setUpViews() {
        binding.imageViewMainProfilePhoto.loadCircleImage(ContactsDataFake.getRandomImage())
    }

    private fun setListeners() {
        binding.buttonMainViewContacts.setOnClickListener {
            navigator.navigate(ProfileFragmentDirections.actionProfileFragmentNavToContactsFragmentNav())
        }
    }
}