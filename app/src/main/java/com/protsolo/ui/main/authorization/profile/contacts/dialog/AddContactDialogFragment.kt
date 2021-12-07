package com.protsolo.ui.main.authorization.profile.contacts.dialog

import android.Manifest
import android.content.pm.PackageManager
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
import com.protsolo.app.data.ContactsDataFake
import com.protsolo.databinding.DialogFragmentAddContactBinding
import com.protsolo.app.item.UserModel
import com.protsolo.app.utils.IntentUtils


class AddContactDialogFragment : DialogFragment() {

    private val viewModelAddContact: AddContactViewModel by viewModels()
    private val intentUtils by lazy { IntentUtils() }
    private val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (intentUtils.isExistIntent(context, uri)) {
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
        navigator.previousBackStackEntry?.savedStateHandle?.remove<UserModel>(Constants.USER_BUNDLE_KEY)
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
                Toast.makeText(requireContext(), Constants.PERMISSION_NEEDED, Toast.LENGTH_LONG).show()
            }
        }
    }
}