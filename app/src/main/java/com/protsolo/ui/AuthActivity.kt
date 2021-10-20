package com.protsolo.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.protsolo.R
import com.protsolo.databinding.ActivityAuthBinding
import com.protsolo.utils.Constants
import com.protsolo.utils.PreferenceStorage
import com.protsolo.utils.extensions.hideKeyboard


class AuthActivity : AppCompatActivity() {

    private val preferencesStorage: PreferenceStorage = PreferenceStorage(this)

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (preferencesStorage.getBoolean(Constants.AUTOLOGIN)) {
            autologin()
        }

        binding.root.hideKeyboard() // todo not working
        setListeners()
    }

    private fun autologin() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun setListeners() {
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
                        textInputLayoutAuthPassword.error = getString(R.string.authMessagePasswordError)
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

    private fun setButtonStatus(binding: ActivityAuthBinding) {
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
        val intent = Intent(this, MainActivity::class.java)
        with(Constants) {
            preferencesStorage.save(
                PREFERENCE_EMAIL_KEY,
                binding.editTextAuthEmailAddressField.text.toString()
            )
            preferencesStorage.save(
                PREFERENCE_PASSWORD_KEY,
                binding.editTextAuthPasswordField.text.toString()
            )
            preferencesStorage.save(
                AUTOLOGIN, binding.checkBoxAuthRememberMe.isChecked)

            intent.putExtra(MESSAGE, binding.editTextAuthEmailAddressField.text.toString())
        }
        startActivity(intent)
        finish()
    }
}