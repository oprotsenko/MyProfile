package com.protsolo.presentation.main.viewPager.authorization.signUp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.protsolo.app.base.BaseFragment
import com.protsolo.app.utils.extensions.hideKeyboard
import com.protsolo.databinding.FragmentSignUpExtBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpExtFragment :
    BaseFragment<FragmentSignUpExtBinding>(FragmentSignUpExtBinding::inflate) {

    private val viewModel: SignUpViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.apply {
            isRegisteredState.observe(viewLifecycleOwner, { registerSuccess ->
                if (registerSuccess) {
                    SignUpExtFragmentDirections.actionSignUpExtFragmentNavToProfileFragmentNav()
                } else {
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun setListeners() {
        binding.apply {
            root.setOnClickListener { root.hideKeyboard() }
            buttonSignUpForward.setOnClickListener {
                viewModel.apply {
                    registerUser(
                        (arguments?.let { it1 -> SignUpExtFragmentArgs.fromBundle(it1).email }).toString(),
                        arguments?.let { SignUpExtFragmentArgs.fromBundle(it).pass }.toString(),
                        editTextSignUpUserName.text.toString(),
                        editTextSignUpPhone.text.toString()
                    )
                }
            }
        }
    }
}