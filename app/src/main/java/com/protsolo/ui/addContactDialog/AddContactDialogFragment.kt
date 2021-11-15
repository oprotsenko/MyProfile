package com.protsolo.ui.addContactDialog

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.protsolo.database.ContactsDataFake
import com.protsolo.databinding.DialogFragmentAddContactBinding
import com.protsolo.ui.addContactDialog.contracts.GetImageFromGalleryContract
import com.protsolo.utils.Constants
import com.protsolo.utils.extensions.loadCircleImage


class AddContactDialogFragment : DialogFragment() {

    private val viewModelAddContact: AddContactViewModel by viewModels()
    private val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private val launcher = registerForActivityResult(GetImageFromGalleryContract()) { uri ->
        if (context?.packageManager?.resolveActivity(
                Intent.getIntentOld(uri.toString()),
                PackageManager.MATCH_DEFAULT_ONLY
            ) != null
        ) {
            loadedImage = uri.toString()
        }
        binding.imageViewAddContactFragmentContactPhoto.loadCircleImage(loadedImage)
    }

    private var isGranted = false
    private var loadedImage = ContactsDataFake.getRandomImage()

    private lateinit var binding: DialogFragmentAddContactBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentAddContactBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        val params: WindowManager.LayoutParams? = dialog?.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.onWindowAttributesChanged(params)
    }

    private fun setObserver() {
        viewModelAddContact.userData.observe(viewLifecycleOwner, {
            parentFragmentManager.setFragmentResult(
                Constants.FRAGMENT_RESULT_LISTENER_KEY,
                bundleOf(Constants.USER_BUNDLE_KEY to it)
            )
        })
    }

    private fun setListeners() {
        binding.apply {
            buttonAddContactSave.setOnClickListener {
                viewModelAddContact.createUser(
                    loadedImage,
                    editTextAddContactsFragmentUsername.text.toString(),
                    editTextAddContactsFragmentCareer.text.toString(),
                    editTextAddContactsFragmentAddress.text.toString(),
                    editTextAddContactsFragmentPhone.text.toString()
                )
                dialog?.dismiss()
            }
            buttonAddContactBack.setOnClickListener {
                dismiss()
            }

            buttonAddContactFragmentAddPhoto.setOnClickListener {
                isGranted = ContextCompat.checkSelfPermission(
                    requireContext(), permissions[0]
                ) == PackageManager.PERMISSION_GRANTED
                checkPermission()
            }
        }
    }

    private fun checkPermission() {
        if (isGranted) {
            launcher.launch("image/*")
        } else {
            requestPermissions(requireActivity(), permissions, Constants.REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == Constants.REQUEST_CODE) {
            isGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
    }
}