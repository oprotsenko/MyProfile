package com.protsolo.presentation.main.viewPager.authorization

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.protsolo.R
import com.protsolo.app.base.BaseFragment
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.extensions.hideKeyboard
import com.protsolo.databinding.FragmentAuthorizationBinding
import com.protsolo.presentation.main.viewPager.ViewPagerAuthorizationFragment
import com.protsolo.presentation.main.viewPager.ViewPagerAuthorizationFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthorizationFragment :
    BaseFragment<FragmentAuthorizationBinding>(FragmentAuthorizationBinding::inflate) {

    private val viewModel: AuthorizationViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.isLoginPage(arguments?.getBoolean(Constants.IS_LOGIN_PAGE) ?: true)
        if (viewModel.isLoginPageView) {
            setUpLoginPageView()
        }
        setObservers()
        setListeners()
    }

    private fun setObservers() {
        viewModel.apply {
            autologinState.observe(viewLifecycleOwner, {
                navigator.navigate(
                    ViewPagerAuthorizationFragmentDirections.actionViewPagerFragmentNavToProfileFragmentNav()
                )
            })

            responseState.observe(viewLifecycleOwner, { responseState ->
                if (responseState) {
                    navigator.navigate(
                        ViewPagerAuthorizationFragmentDirections.actionViewPagerFragmentNavToProfileFragmentNav()
                    )
                } else {
                    Toast.makeText(requireContext(), Constants.FAIL_LOGIN_MESSAGE, Toast.LENGTH_SHORT)
                        .show()
                }
            })

            proceedPermissionState.observe(viewLifecycleOwner, { permissionIsAllowed ->
                if (permissionIsAllowed) {
                    binding.apply {
                        saveAutologinState(
                            checkBoxAuthRememberMe.isChecked
                        )
                        if (isLoginPageView) {
                            loginRequest(
                                editTextAuthEmailAddressField.text.toString(),
                                editTextAuthPasswordField.text.toString()
                            )
                        } else {
                            navigator.navigate(
                                ViewPagerAuthorizationFragmentDirections.actionViewPagerFragmentNavToSignUpExtFragment(
                                    editTextAuthEmailAddressField.text.toString(),
                                    editTextAuthPasswordField.text.toString()
                                )
                            )
                        }
                    }
                }
            })

            emailValidationState.observe(viewLifecycleOwner, { isCorrectEmail ->
                binding.apply {
                    if (!isCorrectEmail) {
                        textInputLayoutAuthEmail.error = getString(R.string.authMessageEmailError)
                    } else {
                        textInputLayoutAuthEmail.isErrorEnabled = false
                    }
                }
            })

            passValidationState.observe(viewLifecycleOwner, { isCorrectPass ->
                binding.apply {
                    if (!isCorrectPass) {
                        textInputLayoutAuthPassword.error =
                            getString(R.string.authMessagePasswordError)
                    } else {
                        textInputLayoutAuthPassword.isErrorEnabled = false
                    }
                }
            })
        }
    }

    private fun setUpLoginPageView() {
        binding.apply {
            textViewAuthTitle.text = resources.getText(R.string.login_hello)
            textViewAuthFillOutDescription.text = resources.getText(R.string.login_description)
            textViewAuthForgotPass.visibility = View.VISIBLE
            customButtonGoogle.visibility = View.GONE
            textViewAuthOr.visibility = View.GONE
            buttonAuthRegister.text = resources.getText(R.string.login_login)
            textViewAuthTermsAndConditions.visibility = View.GONE
            textViewAuthHaveAccount.text = resources.getText(R.string.login_do_not_have_account)
            textViewAuthSignInUp.text = resources.getText(R.string.login_sign_up)
        }
    }

    private fun setListeners() {
        binding.apply {

            root.setOnClickListener { root.hideKeyboard() }

            customButtonGoogle.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")))
            }
            textViewAuthSignInUp.setOnClickListener {
                val onPagerClickListener = parentFragment as ViewPagerAuthorizationFragment
                onPagerClickListener.onPagerItemChange()
            }
            buttonAuthRegister.setOnClickListener {
                viewModel.setPermissionState(
                    editTextAuthEmailAddressField.text.toString(),
                    editTextAuthPasswordField.text.toString()
                )
            }
        }
    }

    companion object {
        fun createFragment(isLoginPage: Boolean) =
            AuthorizationFragment().apply {
                arguments = Bundle()
                arguments?.putBoolean(Constants.IS_LOGIN_PAGE, isLoginPage)
            }
    }
}