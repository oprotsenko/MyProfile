package com.protsolo.ui.addContactDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.protsolo.databinding.DialogFragmentAddContactBinding
import com.protsolo.itemModel.UserModel
import com.protsolo.ui.contactsList.adapters.IContactListItemClickListener
import com.protsolo.utils.Constants
import com.protsolo.utils.extensions.loadCircleImageWithGlide

class AddContactDialogFragment(
    private val onIContactListItemClickListener: IContactListItemClickListener
) : DialogFragment() {

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
        binding.imageViewAddContactFragmentContactPhoto
            .loadCircleImageWithGlide(Constants.DEFAULT_IMAGE)
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
        binding.apply {
            buttonAddContactSave.setOnClickListener {
                onIContactListItemClickListener.addItem(
                    UserModel(
                        0,
                        Constants.DEFAULT_IMAGE,
                        editTextAddContactsFragmentUsername.text.toString(),
                        editTextAddContactsFragmentCareer.text.toString(),
                        editTextAddContactsFragmentAddress.text.toString()
                    ),
                    0
                )
                dialog?.dismiss()
            }
            buttonAddContactBack.setOnClickListener {
                dismiss()
            }
        }
    }
}