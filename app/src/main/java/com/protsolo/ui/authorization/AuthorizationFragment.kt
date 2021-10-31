package com.protsolo.ui.authorization

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import com.protsolo.R
import com.protsolo.databinding.FragmentAuthorizationBinding
import com.protsolo.ui.base.BaseFragment
import com.protsolo.utils.Constants
import com.protsolo.utils.GlobalVal


class AuthorizationFragment : BaseFragment<FragmentAuthorizationBinding>() {

    private lateinit var authorizationViewModel: AuthorizationViewModel

    override fun getViewBinding(): FragmentAuthorizationBinding =
        FragmentAuthorizationBinding.inflate(layoutInflater)

    override fun setUpViews() {
        authorizationViewModel = ViewModelProvider(this).get(AuthorizationViewModel::class.java)
        if (preferenceStorage.getBoolean(Constants.AUTOLOGIN)) {
            autologin()
        }
        extractArguments()
    }

    private fun extractArguments() {
        with(binding) {
            editTextAuthEmailAddressField.setText(arguments?.getString(Constants.PREFERENCE_EMAIL_KEY))
            editTextAuthPasswordField.setText(arguments?.getString(Constants.PREFERENCE_PASSWORD_KEY))
        }
    }

    override fun setListeners() {
        binding.apply {
            editTextAuthEmailAddressField.post {
                editTextAuthEmailAddressField.doOnTextChanged { text, _, _, _ ->
                    if (authorizationViewModel.isValidMail(text)) {
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
                    if (authorizationViewModel.isValidPassword(text)) {
                        textInputLayoutAuthPassword.isErrorEnabled = false
                    } else {
                        textInputLayoutAuthPassword.error =
                            getString(R.string.authMessagePasswordError)
                        textInputLayoutAuthPassword.isErrorEnabled = true
                    }
                    setButtonStatus()
                }
            }
            buttonAuthRegister.setOnClickListener {
                buttonAuthRegister.isEnabled = false // to prevent double click
                register()
            }
            customButtonGoogle.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")))
            }
        }
    }

    private fun autologin() {
        if (GlobalVal.NAV_GRAPH) {
            val action: NavDirections =
                AuthorizationFragmentDirections.actionAuthorizationFragmentToMainPageFragment(
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
            buttonAuthRegister.isEnabled =
                authorizationViewModel.isValidMail(textInputLayoutAuthEmail.editText?.text)
                        && authorizationViewModel.isValidPassword(editTextAuthPasswordField.text)
        }
    }

    private fun register() {
        writeToPreferenceStorage()
        if (GlobalVal.NAV_GRAPH) {
            val action: NavDirections =
                AuthorizationFragmentDirections.actionAuthorizationFragmentToMainPageFragment(
                    binding.editTextAuthEmailAddressField.text.toString()
                )
            listener?.onNavigateToFragment(action)
        } else {
            listener?.onNavigateToFragment(Constants.MAIN_PAGE, args)
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
                AUTOLOGIN, binding.checkBoxAuthRememberMe.isChecked
            )
            args.putString(
                PREFERENCE_EMAIL_KEY,
                binding.editTextAuthEmailAddressField.text.toString()
            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(args: Bundle) =
            AuthorizationFragment().apply {
                arguments = args
            }
    }
}