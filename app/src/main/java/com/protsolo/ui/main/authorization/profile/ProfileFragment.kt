package com.protsolo.ui.main.authorization.profile

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.protsolo.app.architecture.BaseFragment
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.extensions.loadCircleImage
import com.protsolo.data.ContactsDataFake
import com.protsolo.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()
    private val permissions = arrayOf(Manifest.permission.READ_CONTACTS)

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
            checkPermission()
        }
    }

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(requireContext(), permissions[0]) ==
                    PackageManager.PERMISSION_GRANTED -> {
                navigator.navigate(ProfileFragmentDirections.actionProfileFragmentNavToContactsFragmentNav())
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(), permissions[0]
            ) -> {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    permissions,
                    Constants.REQUEST_CODE
                )
                checkPermission()
            }
            else -> {
                Toast.makeText(requireContext(), Constants.PERMISSION_NEEDED, Toast.LENGTH_LONG).show()
            }
        }
    }
}