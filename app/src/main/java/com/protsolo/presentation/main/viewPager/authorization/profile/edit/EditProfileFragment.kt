package com.protsolo.presentation.main.viewPager.authorization.profile.edit

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.protsolo.app.base.BaseFragment
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.IntentUtils
import com.protsolo.data.ContactsDataFake
import com.protsolo.databinding.FragmentEditProfileBinding


class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {

    private val viewModel: EditProfileViewModel by viewModel()
    private val intentUtils by lazy { IntentUtils() }
    private val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (intentUtils.isExistIntent(context, uri)) {
            loadedImage = uri.toString()
        }
        binding.imageViewAddContactFragmentContactPhoto.loadCircleImage(loadedImage)
    }
    private var loadedImage = ContactsDataFake.getRandomImage()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        setListeners()
    }

    private fun setObserver() {
        viewModel.editResponse.observe(viewLifecycleOwner, {
            if (it.isSuccessful) {
                navigator.popBackStack()
            }
        })
    }

    private fun setListeners() {
        binding.apply {
            layoutAddContactHeader.setOnClickListener { root.hideKeyboard() }

            buttonAddContactSave.setOnClickListener {
                viewModel.editProfile(
                    editTextAddContactsFragmentUsername.text.toString(),
                    editTextAddContactsFragmentCareer.text.toString(),
                    editTextAddContactsFragmentAddress.text.toString(),
                    editTextAddContactsFragmentPhone.text.toString(),
                    editTextAddContactsFragmentBirthDay.text.toString(),
                    loadedImage
                )
            }

            buttonAddContactBack.setOnClickListener {
                navigator.popBackStack()
            }

            buttonAddContactFragmentAddPhoto.setOnClickListener {
                checkPermission()
            }
        }
    }

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(requireContext(), permissions[0]) ==
                    PackageManager.PERMISSION_GRANTED -> {
                launcher.launch(Constants.READ_FILE_TYPE)
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(), permissions[0]
            ) -> {
                requestPermissions(requireActivity(), permissions, Constants.REQUEST_CODE)
                checkPermission()
            }
            else -> {
                Toast.makeText(requireContext(), Constants.PERMISSION_NEEDED, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}