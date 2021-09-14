package com.protsolo

import PreferenceStorage
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.protsolo.databinding.ActivityAuthBinding
import com.protsolo.utils.Constants


class AuthActivity : AppCompatActivity() {

    private val preferencesStorage: PreferenceStorage = PreferenceStorage(this)

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            editTextAuthEmailAddressField.doOnTextChanged { text, _, _, _ ->
                if (isValidMail(text)) {
                    textInputLayoutAuthEmail.isErrorEnabled = false
                } else {
                    textInputLayoutAuthEmail.error = getString(R.string.authMessageEmailError)
                    textInputLayoutAuthEmail.isErrorEnabled = true
                }
                setButtonStatus(binding)
            }
            editTextAuthPasswordField.doOnTextChanged { text, _, _, _ ->
                if (isValidPassword(text)) {
                    textInputLayoutAuthPassword.isErrorEnabled = false
                } else {
                    textInputLayoutAuthPassword.error = getString(R.string.authMessagePasswordError)
                    textInputLayoutAuthPassword.isErrorEnabled = true
                }
                setButtonStatus(binding)
            }
            buttonAuthRegister.setOnClickListener {
                buttonAuthRegister.isEnabled = false // to prevent double click
                register()
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
        preferencesStorage.save(Constants.PREFERENCE_EMAIL_KEY, binding.editTextAuthEmailAddressField.text.toString())
        preferencesStorage.save(Constants.PREFERENCE_PASSWORD_KEY, binding.editTextAuthPasswordField.text.toString())
        intent.putExtra(Constants.MESSAGE, binding.editTextAuthEmailAddressField.text.toString())
        startActivity(intent)
        finish()
    }
}