package com.protsolo.ui.contactDetailView

import android.os.Bundle
import com.protsolo.databinding.FragmentContactDetailViewBinding
import com.protsolo.itemModel.UserModel
import com.protsolo.ui.baseFragment.BaseFragment
import com.protsolo.utils.Constants
import com.protsolo.utils.GlobalVal
import com.protsolo.utils.extensions.loadImageWithFresco

class ContactDetailViewFragment : BaseFragment<FragmentContactDetailViewBinding>() {

    override fun getViewBinding(): FragmentContactDetailViewBinding =
        FragmentContactDetailViewBinding.inflate(layoutInflater)

    override fun setUpViews() {
        val args = getARGS()

        if (args != null) {
            extractArguments(args)
        }
    }

    override fun setListeners() {
        binding.buttonContactDetailViewBack.setOnClickListener {
            listener?.onBackButtonPressed()
        }
    }

    private fun extractArguments(args: Any) {
        val user = args as UserModel
        with(binding) {
            imageViewContactDetailViewProfilePhoto.loadImageWithFresco(user.image)
            textViewContactDetailViewUserName.text = user.name
            textViewContactDetailViewCareer.text = user.career
            textViewContactDetailViewHomeAddress.text = user.address
        }
    }

    private fun getARGS() = if (GlobalVal.NAV_GRAPH) {
        arguments?.let { ContactDetailViewFragmentArgs.fromBundle(it).userDetails }
    } else
    arguments?.get(Constants.BUNDLE_KEY)

    companion object {
        @JvmStatic
        fun newInstance(args: Bundle) =
            ContactDetailViewFragment().apply {
                arguments = args
            }
    }
}