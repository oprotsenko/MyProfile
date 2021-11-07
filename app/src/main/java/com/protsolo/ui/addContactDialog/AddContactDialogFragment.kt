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
        binding.apply {
            buttonAddContactSave.setOnClickListener {
                val user = addContactViewModel.createUser(editTextAddContactsFragmentUsername.text.toString(),
                    editTextAddContactsFragmentCareer.text.toString(),
                    editTextAddContactsFragmentAddress.text.toString(),
                    editTextAddContactsFragmentPhone.text.toString())
                onIContactItemClickListener.addItem(user, 0)
                dialog?.dismiss()
            }
            buttonAddContactBack.setOnClickListener {
                dismiss()
            }
        }
    }
}