package com.protsolo.ui.addContactDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.protsolo.databinding.DialogFragmentAddContactBinding
import com.protsolo.ui.contacts.adapters.IContactItemClickListener
import com.protsolo.database.ContactsDataFake
import com.protsolo.utils.extensions.loadCircleImage


class AddContactDialogFragment(
    private val onIContactItemClickListener: IContactItemClickListener
) : DialogFragment() {

    private val addContactViewModel: AddContactViewModel by viewModels()

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
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        val params: WindowManager.LayoutParams? = dialog?.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.onWindowAttributesChanged(params)
    }

    private fun setListeners() {
        var loadedImage = ContactsDataFake.getRandomImage()

        binding.apply {
            val launcher = registerForActivityResult(GetImageFromGalleryContract()) {
                if (it != null) {
                    loadedImage = it.toString()
                }
                imageViewAddContactFragmentContactPhoto.loadCircleImage(loadedImage)
            }
            buttonAddContactSave.setOnClickListener {
                val user = addContactViewModel.createUser(loadedImage,
                    editTextAddContactsFragmentUsername.text.toString(),
                    editTextAddContactsFragmentCareer.text.toString(),
                    editTextAddContactsFragmentAddress.text.toString(),
                    editTextAddContactsFragmentPhone.text.toString())

                onIContactItemClickListener.addItem(user, 0)
                dialog?.dismiss()
            }
            buttonAddContactBack.setOnClickListener {
                dismiss()
            }

            buttonAddContactFragmentAddPhoto.setOnClickListener {
                launcher.launch("image/*")
            }
        }
    }
}