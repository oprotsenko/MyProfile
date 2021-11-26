package com.protsolo.ui.main.authorization

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.protsolo.R
import com.protsolo.app.architecture.BaseFragment
import com.protsolo.app.utils.Validator
import com.protsolo.app.utils.extensions.hideKeyboard
import com.protsolo.databinding.FragmentAuthorizationBinding
import com.protsolo.ui.main.authorization.viewPager.IViewPagerListener
import com.protsolo.ui.main.authorization.viewPager.ViewPagerFragmentDirections

class AuthorizationFragment :
    BaseFragment<FragmentAuthorizationBinding>() {

    private val viewModel: AuthorizationViewModel by viewModels()

    override fun getViewBinding(): FragmentAuthorizationBinding =
        FragmentAuthorizationBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObservers()
        setUpViews()
        setListeners()
    }

    private fun setUpViews() {
        if (isLoginPage) {
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
    }

    private fun setObservers() {
        viewModel.isAutologin.observe(viewLifecycleOwner, {
            navigator.navigate(
                ViewPagerFragmentDirections.actionViewPagerFragmentNavToProfileFragmentNav()
            )
        })

        viewModel.enteredEmail.observe(viewLifecycleOwner, {
            viewModel.isValid()
        })

        viewModel.enteredPass.observe(viewLifecycleOwner, {
            viewModel.isValid()
        })
    }

    private fun setListeners() {
        binding.apply {

            root.setOnClickListener { root.hideKeyboard() }

            customButtonGoogle.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")))
            }
            textViewAuthSignInUp.setOnClickListener {
                onPagerClickListener?.onClick()
            }
            editTextAuthEmailAddressField.doOnTextChanged { email, _, _, _ ->
                viewModel.setEmail(email.toString())
            }
            editTextAuthPasswordField.doOnTextChanged { pass, _, _, _ ->
                viewModel.setPass(pass.toString())
            }
            buttonAuthRegister.setOnClickListener {
                if (viewModel.isValidData.value == true) {
                    buttonAuthRegister.isEnabled = false // to prevent double click
                    register()
                } else {
                    setErrors()
                }
            }
        }
    }

    private fun setErrors() {
        binding.apply {
            if (!Validator.isValidMail(viewModel.enteredEmail.value)) {
                textInputLayoutAuthEmail.error = getString(R.string.authMessageEmailError)
                textInputLayoutAuthEmail.isErrorEnabled = true
            } else {
                textInputLayoutAuthEmail.isErrorEnabled = false
            }
            if (!Validator.isValidPassword(viewModel.enteredPass.value)) {
                textInputLayoutAuthPassword.error =
                    getString(R.string.authMessagePasswordError)
                textInputLayoutAuthPassword.isErrorEnabled = true
            } else {
                textInputLayoutAuthPassword.isErrorEnabled = false
            }
        }
    }

    private fun register() {
        binding.apply {
            viewModel.writeToPreferenceStorage(
                editTextAuthEmailAddressField.text.toString(),
                editTextAuthPasswordField.text.toString(),
                checkBoxAuthRememberMe.isChecked
            )
            navigator.navigate(
                ViewPagerFragmentDirections.actionViewPagerFragmentNavToProfileFragmentNav()
            )
        }
    }

    companion object {
        var isLoginPage = true
        var onPagerClickListener: IViewPagerListener? = null
    }
}