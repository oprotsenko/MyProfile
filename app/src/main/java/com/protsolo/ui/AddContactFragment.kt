package com.protsolo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.protsolo.R
import com.protsolo.adapters.ContactsAdapter
import com.protsolo.databinding.AddContactFragmentBinding
import com.protsolo.utils.Constants
import com.protsolo.utils.loadCircleImageWithGlide

class AddContactFragment(private val adapter: ContactsAdapter) : DialogFragment(),
    View.OnClickListener {

    private lateinit var binding: AddContactFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = AddContactFragmentBinding.inflate(layoutInflater)
        binding.imageViewAddContactFragmentContactPhoto.loadCircleImageWithGlide(Constants.DEFAULT_IMAGE)
        setListeners()

        return inflater.inflate(R.layout.add_contact_fragment, null)
    }

    private fun setListeners() {
        binding.apply {
            buttonAddContactSave.setOnClickListener {
//                var message = StringBuilder()
//                message.append(editTextAddContactsFragmentUsername.text).append(",")
//                    .append(editTextAddContactsFragmentCareer.text).append(",")
//                    .append(editTextAddContactsFragmentAddress.text)
//                adapter.addItem(UserModel(0, Constants.DEFAULT_IMAGE, editTextAddContactsFragmentUsername.text.toString(),
//                    editTextAddContactsFragmentCareer.text.toString(), editTextAddContactsFragmentAddress.text.toString()), 0)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        var params: WindowManager.LayoutParams? = dialog?.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.onWindowAttributesChanged(params)
        binding.imageViewAddContactFragmentContactPhoto.loadCircleImageWithGlide(Constants.DEFAULT_IMAGE)
    }

    override fun onClick(v: View?) {
        binding.apply {
            buttonAddContactSave.setOnClickListener {
//                var message = StringBuilder()
//                message.append(editTextAddContactsFragmentUsername.text).append(",")
//                    .append(editTextAddContactsFragmentCareer.text).append(",")
//                    .append(editTextAddContactsFragmentAddress.text)
//                adapter.addItem(UserModel(Constants.DEFAULT_IMAGE, editTextAddContactsFragmentUsername.text.toString(),
//                    editTextAddContactsFragmentCareer.text.toString(), editTextAddContactsFragmentAddress.text.toString()), 0)
            }
        }
    }

}
