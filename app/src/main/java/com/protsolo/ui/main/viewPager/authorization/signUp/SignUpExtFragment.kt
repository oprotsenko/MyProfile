package com.protsolo.ui.main.viewPager.authorization.signUp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.protsolo.app.base.BaseFragment
import com.protsolo.app.utils.extensions.hideKeyboard
import com.protsolo.databinding.FragmentSignUpExtBinding
import kotlinx.coroutines.launch
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
                    email =
                        (arguments?.let { it1 -> SignUpExtFragmentArgs.fromBundle(it1).email }).toString()
                    pass = arguments?.let { SignUpExtFragmentArgs.fromBundle(it).pass }.toString()
                    userName = editTextSignUpUserName.text.toString()
                    phone = editTextSignUpPhone.text.toString()

                    viewModelScope.launch { registerUser() }
                }
            }
        }
    }
}