package com.protsolo.ui.main.authorization

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.protsolo.R
import com.protsolo.app.base.BaseFragment
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.extensions.hideKeyboard
import com.protsolo.databinding.FragmentAuthorizationBinding
import com.protsolo.ui.main.authorization.viewPager.ViewPagerFragment
import com.protsolo.ui.main.authorization.viewPager.ViewPagerFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthorizationFragment :
    BaseFragment<FragmentAuthorizationBinding>(FragmentAuthorizationBinding::inflate) {

    private val viewModel: AuthorizationViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.isLoginPage(arguments?.getBoolean(Constants.IS_LOGIN_PAGE) ?: true)
        if (viewModel.setUpLoginPageView) {
            setUpLoginPageView()
        }
        setObservers()
        setListeners()
    }

    private fun setObservers() {
        viewModel.apply {
            autologinState.observe(viewLifecycleOwner, {
                navigator.navigate(
                    ViewPagerFragmentDirections.actionViewPagerFragmentNavToProfileFragmentNav()
                )
            })

            registerPermissionState.observe(viewLifecycleOwner, { permissionIsAllowed ->
                if (permissionIsAllowed) {
                    navigator.navigate(
                        ViewPagerFragmentDirections.actionViewPagerFragmentNavToProfileFragmentNav()
                    )
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

            editTextAuthEmailAddressField.doOnTextChanged { _, _, _, _ ->
                textInputLayoutAuthEmail.isErrorEnabled = false
            }

            editTextAuthPasswordField.doOnTextChanged { _, _, _, _ ->
                textInputLayoutAuthPassword.isErrorEnabled = false
            }

            customButtonGoogle.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")))
            }

            textViewAuthSignInUp.setOnClickListener {
                (parentFragment as ViewPagerFragment).onPagerItemChange()
            }

            buttonAuthRegister.setOnClickListener {
                binding.apply {
                    viewModel.apply {
                        rememberMe = checkBoxAuthRememberMe.isChecked
                        register(
                            editTextAuthEmailAddressField.text.toString(),
                            editTextAuthPasswordField.text.toString()
                        )
                    }
                }
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