package com.protsolo.ui.main.authorization.profile.contacts.dialog

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.protsolo.R
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.extensions.hideKeyboard
import com.protsolo.app.utils.extensions.loadCircleImage
import com.protsolo.data.ContactsDataFake
import com.protsolo.databinding.DialogFragmentAddContactBinding
import com.protsolo.itemModel.UserModel


class AddContactDialogFragment : DialogFragment() {

    private val viewModelAddContact: AddContactViewModel by viewModels()
    private val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (isExistIntent(uri)) {
            loadedImage = uri.toString()
        }
        binding.imageViewAddContactFragmentContactPhoto.loadCircleImage(loadedImage)
    }
    private var loadedImage = ContactsDataFake.getRandomImage()
    private lateinit var navigator: NavController
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
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navigator = navHostFragment.navController
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == Constants.REQUEST_CODE) {
            launcher.launch(Constants.READ_FILE_TYPE)
        } else {
            Toast.makeText(requireContext(),
                requireContext().getString(R.string.add_contact_permission_needed),
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun setObserver() {
        viewModelAddContact.userData.observe(viewLifecycleOwner, {
            navigator.previousBackStackEntry?.savedStateHandle?.set(Constants.USER_BUNDLE_KEY, it)
        })
    }

    private fun setListeners() {
        binding.apply {
            layoutAddContactHeader.setOnClickListener { root.hideKeyboard() }
            buttonAddContactSave.setOnClickListener {
                viewModelAddContact.createUser(
                    loadedImage,
                    editTextAddContactsFragmentUsername.text.toString(),
                    editTextAddContactsFragmentCareer.text.toString(),
                    editTextAddContactsFragmentAddress.text.toString(),
                    editTextAddContactsFragmentPhone.text.toString()
                )
                navigator.popBackStack()
            }
            buttonAddContactBack.setOnClickListener {
                navigator.previousBackStackEntry?.savedStateHandle?.remove<UserModel>(Constants.USER_BUNDLE_KEY)
                navigator.popBackStack()
            }

            buttonAddContactFragmentAddPhoto.setOnClickListener {
                if (ContextCompat.checkSelfPermission(requireContext(), permissions[0]) ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    launcher.launch(Constants.READ_FILE_TYPE)
                } else {
                    requestPermission()
                }
            }
        }
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                permissions[0]
            )
        ) {
            AlertDialog.Builder(requireContext())
                .setTitle(requireContext().getString(R.string.add_contact_permission_needed_title))
                .setMessage(requireContext().getString(R.string.add_contact_permission_needed_message))
                .setPositiveButton(requireContext().getString(R.string.add_contact_allow)) { _, _ ->
                    requestPermissions(
                        requireActivity(),
                        permissions,
                        Constants.REQUEST_CODE
                    )
                }
                .setNegativeButton(requireContext().getString(R.string.add_contact_cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .create().show()
        } else {
            requestPermissions(requireActivity(), permissions, Constants.REQUEST_CODE)
        }
    }

    private fun isExistIntent(uri: Uri?) = context?.packageManager?.resolveActivity(
        Intent.getIntentOld(uri.toString()),
        PackageManager.MATCH_DEFAULT_ONLY
    ) != null
}