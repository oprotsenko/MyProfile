package com.protsolo.ui.main.authorization

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.protsolo.R
import com.protsolo.databinding.FragmentAuthorizationBinding
import com.protsolo.ui.main.MainActivity
import com.protsolo.app.architecture.BaseFragment
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.Validator

class AuthorizationFragment : BaseFragment<FragmentAuthorizationBinding>() {

    private val viewModelAuthorization: AuthorizationViewModel by viewModels()

    override fun getViewBinding(): FragmentAuthorizationBinding =
        FragmentAuthorizationBinding.inflate(layoutInflater)

    override fun setUpViews() {
        if (MainActivity.isLoginPage) {
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
        if (viewModelAuthorization.isAutologin()) {
            autologin()
        }

        with(binding) {
            editTextAuthEmailAddressField.setText(
                viewModelAuthorization.getEmail(arguments)
            )
            editTextAuthPasswordField.setText(
                viewModelAuthorization.getPass(arguments)
            )
        }
    }

    override fun setListeners() {
        viewListeners()
        editTextFieldListeners()
    }

    private fun viewListeners() {
        binding.apply {

            root.setOnClickListener { root.hideKeyboard() }

            buttonAuthRegister.setOnClickListener {
                buttonAuthRegister.isEnabled = false // to prevent double click
                register()
            }
            customButtonGoogle.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")))
            }
            textViewAuthSignInUp.setOnClickListener {
                MainActivity.isLoginPage = !MainActivity.isLoginPage
                val bundle = Bundle()
                bundle.putString(Constants.FRAGMENT_BUNDLE_KEY, Constants.AUTHORIZATION_FRAGMENT)
                bundle.putString(Constants.PREFERENCE_EMAIL_KEY, editTextAuthEmailAddressField.text.toString())
                bundle.putString(Constants.PREFERENCE_PASSWORD_KEY, editTextAuthPasswordField.text.toString())
                navigator?.goToFragment(bundle)
            }
        }
    }

    private fun editTextFieldListeners() {
        binding.apply {
            editTextAuthEmailAddressField.post {
                editTextAuthEmailAddressField.doOnTextChanged { text, _, _, _ ->
                    if (Validator.isValidMail(text)) {
                        textInputLayoutAuthEmail.isErrorEnabled = false
                    } else {
                        textInputLayoutAuthEmail.error = getString(R.string.authMessageEmailError)
                        textInputLayoutAuthEmail.isErrorEnabled = true
                    }
                    setButtonStatus()
                }
            }
            editTextAuthPasswordField.post {
                editTextAuthPasswordField.doOnTextChanged { text, _, _, _ ->
                    if (Validator.isValidPassword(text)) {
                        textInputLayoutAuthPassword.isErrorEnabled = false
                    } else {
                        textInputLayoutAuthPassword.error =
                            getString(R.string.authMessagePasswordError)
                        textInputLayoutAuthPassword.isErrorEnabled = true
                    }
                    setButtonStatus()
                }
            }
        }
    }

    private fun autologin() {
        val email = viewModelAuthorization.getEmailFromPref()
        val bundle = Bundle()
        bundle.putString(Constants.FRAGMENT_BUNDLE_KEY, Constants.MAIN_FRAGMENT)
        bundle.putString(Constants.PREFERENCE_EMAIL_KEY, email)
        navigator?.goToFragment(bundle)
    }

    private fun setButtonStatus() {
        binding.apply {
            buttonAuthRegister.isEnabled = Validator.isValidData(
                editTextAuthEmailAddressField.text,
                editTextAuthPasswordField.text
            )
        }
    }

    private fun register() {
        binding.apply {
            viewModelAuthorization.writeToPreferenceStorage(
                editTextAuthEmailAddressField.text.toString(),
                editTextAuthPasswordField.text.toString(),
                checkBoxAuthRememberMe.isChecked
            )
            val bundle = Bundle()
            bundle.putString(Constants.FRAGMENT_BUNDLE_KEY, Constants.MAIN_FRAGMENT)
            bundle.putString(Constants.PREFERENCE_EMAIL_KEY, editTextAuthEmailAddressField.text.toString())
            navigator?.goToFragment(bundle)
        }
    }

    companion object {
        fun newInstance(args: Bundle): AuthorizationFragment =
            AuthorizationFragment().apply {
                arguments = args
            }
    }
}