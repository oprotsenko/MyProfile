package com.protsolo.ui.authorization

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.protsolo.R
import com.protsolo.databinding.FragmentAuthorizationBinding
import com.protsolo.ui.ActivityMain
import com.protsolo.ui.baseFragment.BaseFragment
import com.protsolo.ui.mainPage.MainPageFragment
import com.protsolo.utils.Constants
import com.protsolo.utils.Constants.NAV_GRAPH
import com.protsolo.utils.Validator
import com.protsolo.utils.extensions.hideKeyboard

class AuthorizationFragment : BaseFragment<FragmentAuthorizationBinding>() {

    private val authorizationViewModel: AuthorizationViewModel by viewModels()

    override fun getViewBinding(): FragmentAuthorizationBinding =
        FragmentAuthorizationBinding.inflate(layoutInflater)

    override fun setUpViews() {
        if (ActivityMain.isLoginPage) {
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
        if (authorizationViewModel.isAutologin()) {
            autologin()
        }

        with(binding) {
            editTextAuthEmailAddressField.setText(
                authorizationViewModel.getEmail(arguments)
            )
            editTextAuthPasswordField.setText(
                authorizationViewModel.getPass(arguments)
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
                ActivityMain.isLoginPage = !ActivityMain.isLoginPage
                if (NAV_GRAPH) {
                    listener?.onNavigateTo(
                        authorizationViewModel.getAction(
                            editTextAuthEmailAddressField.text.toString(),
                            editTextAuthPasswordField.text.toString()
                        )
                    )
                } else {
                    listener?.onTransactionTo(
                        authorizationViewModel.getFragment(
                            editTextAuthEmailAddressField.text.toString(),
                            editTextAuthPasswordField.text.toString(),
                            args
                        )
                    )
                }
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
        val email = preferenceStorage.getString(Constants.PREFERENCE_EMAIL_KEY)
        if (NAV_GRAPH) {
            listener?.onNavigateTo(
                AuthorizationFragmentDirections.actionAuthorizationFragmentToMainPageFragment(email)
            )
        } else {
            args.putString(Constants.PREFERENCE_EMAIL_KEY, email)
            listener?.onTransactionTo(MainPageFragment.newInstance(args))
        }
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
        writeToPreferenceStorage()
        if (NAV_GRAPH) {
            listener?.onNavigateTo(
                AuthorizationFragmentDirections.actionAuthorizationFragmentToMainPageFragment(
                    binding.editTextAuthEmailAddressField.text.toString()
                )
            )
        } else {
            args.putString(
                Constants.PREFERENCE_EMAIL_KEY,
                binding.editTextAuthEmailAddressField.text.toString()
            )
            listener?.onTransactionTo(MainPageFragment.newInstance(args))
        }
    }

    private fun writeToPreferenceStorage() {
        with(Constants) {
            preferenceStorage.save(
                PREFERENCE_EMAIL_KEY,
                binding.editTextAuthEmailAddressField.text.toString()
            )
            preferenceStorage.save(
                PREFERENCE_PASSWORD_KEY,
                binding.editTextAuthPasswordField.text.toString()
            )
            preferenceStorage.save(
                PREFERENCE_AUTOLOGIN, binding.checkBoxAuthRememberMe.isChecked
            )
        }
    }

    companion object {
        fun newInstance(args: Bundle): AuthorizationFragment =
            AuthorizationFragment().apply {
                arguments = args
            }
    }
}