package com.protsolo.ui.login

import androidx.core.widget.doOnTextChanged
import androidx.navigation.NavDirections
import com.protsolo.R
import com.protsolo.databinding.FragmentLoginBinding
import com.protsolo.ui.FIRST_LOGIN
import com.protsolo.ui.baseFragment.BaseFragment
import com.protsolo.utils.Constants
import com.protsolo.utils.GlobalVal
import com.protsolo.utils.Validator

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override fun getViewBinding(): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater)

    override fun setUpViews() {
        if (preferenceStorage.getBoolean(Constants.AUTOLOGIN) && FIRST_LOGIN) {
            FIRST_LOGIN = false
            autologin()
        }
    }

    override fun setListeners() {
        binding.apply {
            editTextLoginEmailAddressField.post {
                editTextLoginEmailAddressField.doOnTextChanged { text, _, _, _ ->
                    if (Validator.isValidMail(text)) {
                        textInputLayoutLoginEmail.isErrorEnabled = false
                    } else {
                        textInputLayoutLoginEmail.error = getString(R.string.authMessageEmailError)
                        textInputLayoutLoginEmail.isErrorEnabled = true
                    }
                    setButtonStatus()
                }
            }
            editTextLoginPasswordField.post {
                editTextLoginPasswordField.doOnTextChanged { text, _, _, _ ->
                    if (Validator.isValidPassword(text)) {
                        textInputLayoutLoginPassword.isErrorEnabled = false
                    } else {
                        textInputLayoutLoginPassword.error =
                            getString(R.string.authMessagePasswordError)
                        textInputLayoutLoginPassword.isErrorEnabled = true
                    }
                    setButtonStatus()
                }
            }
            buttonLogin.setOnClickListener {
                buttonLogin.isEnabled = false // to prevent double click
                register()
            }
            textViewLoginSignUp.setOnClickListener {
                if (GlobalVal.NAV_GRAPH) {
                    val action: NavDirections =
                        LoginFragmentDirections.actionLoginFragmentToAuthorizationFragment()
                    listener?.onNavigateToFragment(action)
                } else {
                    args.putString(
                        Constants.PREFERENCE_EMAIL_KEY,
                        editTextLoginEmailAddressField.text.toString()
                    )
                    args.putString(
                        Constants.PREFERENCE_PASSWORD_KEY,
                        editTextLoginPasswordField.text.toString()
                    )
                    listener?.onNavigateToFragment(Constants.AUTHORIZATION, args)
                }
            }
        }
    }

    private fun autologin() {
        if (GlobalVal.NAV_GRAPH) {
            val action: NavDirections =
                LoginFragmentDirections.actionLoginFragmentToMainPageFragment(
                    preferenceStorage.getString(Constants.PREFERENCE_EMAIL_KEY)
                )
            listener?.onNavigateToFragment(action)
        } else {
            args.putString(
                Constants.PREFERENCE_EMAIL_KEY,
                preferenceStorage.getString(Constants.PREFERENCE_EMAIL_KEY)
            )
            listener?.onNavigateToFragment(Constants.MAIN_PAGE, args)
        }
    }

    private fun setButtonStatus() {
        binding.apply {
            buttonLogin.isEnabled =
                Validator.isValidMail(textInputLayoutLoginEmail.editText?.text)
                        && Validator.isValidPassword(editTextLoginPasswordField.text)
        }
    }

    private fun register() {
        writeToPreferenceStorage()
        with(binding) {
            if (GlobalVal.NAV_GRAPH) {
                val action: NavDirections =
                    LoginFragmentDirections.actionLoginFragmentToMainPageFragment(
                        editTextLoginEmailAddressField.text.toString()
                    )
                listener?.onNavigateToFragment(action)
            } else {
                args.putString(
                    Constants.BUNDLE_KEY,
                    editTextLoginEmailAddressField.text.toString()
                )
                listener?.onNavigateToFragment(Constants.MAIN_PAGE, args)
            }
        }
    }

    private fun writeToPreferenceStorage() {
        with(Constants) {
            preferenceStorage.save(
                PREFERENCE_EMAIL_KEY,
                binding.editTextLoginEmailAddressField.text.toString()
            )
            preferenceStorage.save(
                PREFERENCE_PASSWORD_KEY,
                binding.editTextLoginPasswordField.text.toString()
            )
            preferenceStorage.save(
                AUTOLOGIN, binding.checkBoxLoginRememberMe.isChecked
            )
            args.putString(
                PREFERENCE_EMAIL_KEY,
                binding.editTextLoginEmailAddressField.text.toString()
            )
        }
    }
}