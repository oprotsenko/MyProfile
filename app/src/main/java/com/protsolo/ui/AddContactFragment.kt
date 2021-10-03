package com.protsolo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.protsolo.adapters.ContactsAdapter
import com.protsolo.databinding.AddContactFragmentBinding
import com.protsolo.itemModel.UserModel
import com.protsolo.utils.Constants
import com.protsolo.utils.loadCircleImageWithGlide

class AddContactFragment(private val contactsAdapter: ContactsAdapter) : DialogFragment() {

    private lateinit var binding: AddContactFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddContactFragmentBinding.inflate(layoutInflater, container,false)
        binding.imageViewAddContactFragmentContactPhoto
            .loadCircleImageWithGlide(Constants.DEFAULT_IMAGE)
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.apply {
            buttonAddContactSave.setOnClickListener {
                contactsAdapter.addNewItem(
                    UserModel(0,
                        Constants.DEFAULT_IMAGE,
                        editTextAddContactsFragmentUsername.text.toString(),
                        editTextAddContactsFragmentCareer.text.toString(),
                        editTextAddContactsFragmentAddress.text.toString()),
                    0)
                dialog?.dismiss()
            }
            buttonAddContactBack.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val params: WindowManager.LayoutParams? = dialog?.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.onWindowAttributesChanged(params)
    }
}
