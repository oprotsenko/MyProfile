package com.protsolo.ui.authorization

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.protsolo.R
import com.protsolo.databinding.FragmentAuthorizationBinding
import com.protsolo.ui.BaseFragment
import com.protsolo.utils.Constants
import com.protsolo.utils.extensions.hideKeyboard


class AuthorizationFragment : BaseFragment<FragmentAuthorizationBinding>() {

    private val args: Bundle = Bundle()

    override fun getViewBinding(): FragmentAuthorizationBinding =
        FragmentAuthorizationBinding.inflate(layoutInflater)

    override fun setUpViews() {
        super.setUpViews()
        if (preferenceStorage.getBoolean(Constants.AUTOLOGIN)) {
            autologin()
        }
    }

    override fun setListeners() {
        binding.apply {
            editTextAuthEmailAddressField.post {
                editTextAuthEmailAddressField.doOnTextChanged { text, _, _, _ ->
                    if (isValidMail(text)) {
                        textInputLayoutAuthEmail.isErrorEnabled = false
                    } else {
                        textInputLayoutAuthEmail.error = getString(R.string.authMessageEmailError)
                        textInputLayoutAuthEmail.isErrorEnabled = true
                    }
                    setButtonStatus(binding)
                }
            }
            editTextAuthPasswordField.post {
                editTextAuthPasswordField.doOnTextChanged { text, _, _, _ ->
                    if (isValidPassword(text)) {
                        textInputLayoutAuthPassword.isErrorEnabled = false
                    } else {
                        textInputLayoutAuthPassword.error =
                            getString(R.string.authMessagePasswordError)
                        textInputLayoutAuthPassword.isErrorEnabled = true
                    }
                    setButtonStatus(binding)
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
        args.putString(
            Constants.PREFERENCE_EMAIL_KEY,
            preferenceStorage.getString(Constants.PREFERENCE_EMAIL_KEY)
        )
        listener?.onNavigateToFragment(Constants.MAIN_PAGE, args)
    }

    private fun setButtonStatus(binding: FragmentAuthorizationBinding) {
        binding.apply {
            buttonAuthRegister.isEnabled =
                isValidMail(textInputLayoutAuthEmail.editText?.text)
                        && isValidPassword(editTextAuthPasswordField.text)
        }
    }

    private fun isValidPassword(text: CharSequence?) =
        Regex(Constants.PASSWORD_PATTERN).matches(text.toString())

    private fun isValidMail(text: CharSequence?) =
        Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()

    private fun register() {
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
        listener?.onNavigateToFragment(Constants.MAIN_PAGE, args)
    }
}