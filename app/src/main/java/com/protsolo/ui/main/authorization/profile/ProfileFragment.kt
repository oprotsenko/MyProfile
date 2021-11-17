package com.protsolo.ui.main.authorization.profile

import androidx.fragment.app.viewModels
import com.protsolo.app.architecture.BaseFragment
import com.protsolo.app.utils.extensions.loadCircleImage
import com.protsolo.data.ContactsDataFake
import com.protsolo.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun getViewBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)

    override fun setUpViews() {
        binding.imageViewMainProfilePhoto.loadCircleImage(ContactsDataFake.getRandomImage())

        setName()
    }

    override fun setListeners() {
        binding.buttonMainViewContacts.setOnClickListener {
            navController.navigate(ProfileFragmentDirections.actionProfileFragmentToContactsFragment())
        }
    }

    private fun setName() {
        val parsedUserName: String =
            viewModel.parseName(viewModel.getName(arguments))
        binding.textViewMainUserName.text = parsedUserName
    }
}