package com.protsolo

import PreferenceStorage
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.protsolo.databinding.ActivityAuthBinding


class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    val preferencesStorage: PreferenceStorage = PreferenceStorage(this)
    val appPreferencesEmail = "email"
    val appPreferencesPass = "password"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            editTextEmailAddressField.doOnTextChanged { text, start, before, count ->
                if (validMail(text)) {
                    textInputLayoutEmail.isErrorEnabled = false
                } else {
                    textInputLayoutEmail.error = "Email is not valid"
                    textInputLayoutEmail.isErrorEnabled = true
                }
                buttonRegister.isEnabled =
                    validMail(textInputLayoutEmail.editText!!.text) && validPassword(
                        editTextPasswordField.text
                    )
            }
            editTextPasswordField.doOnTextChanged { text, start, before, count ->
                if (validPassword(text)) {
                    textInputLayoutPassword.isErrorEnabled = false
                } else {
                    textInputLayoutPassword.error = "Use at last 8 symbols"
                    textInputLayoutPassword.isErrorEnabled = true
                }
                buttonRegister.isEnabled =
                    validMail(textInputLayoutEmail.editText!!.text) && validPassword(
                        editTextPasswordField.text
                    )
            }
            buttonRegister.setOnClickListener {
                buttonRegister.isEnabled = false
                register()
            }
        }
    }

    private fun validPassword(text: CharSequence?) =
        Regex("[a-z0-9?:!#$%&]{8,}").matches(text.toString())

    private fun validMail(text: CharSequence?) =
        Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()

    private fun register() {
        val intent = Intent(this, MainActivity::class.java)
        preferencesStorage.save(appPreferencesEmail, binding.editTextEmailAddressField.text.toString())
        preferencesStorage.save(appPreferencesPass, binding.editTextPasswordField.text.toString())
        intent.putExtra("name", binding.editTextEmailAddressField.text.toString())
        startActivity(intent)
        finish()
    }
}