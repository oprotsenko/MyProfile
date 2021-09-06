package com.protsolo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import android.icu.text.IDNA
import android.util.Patterns
import androidx.core.widget.doOnTextChanged
import com.protsolo.databinding.ActivityAuthBinding
import java.lang.Error
import kotlin.Error

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

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
                    buttonRegister.isEnabled = true
                } else {
                    buttonRegister.isEnabled = false
                    editTextEmailAddressField.error = "wrong"
                }
            }
            editTextPasswordField.doOnTextChanged { text, start, before, count ->
                if (validPassword(text)) {
                    buttonRegister.isEnabled = true
                } else {
                    buttonRegister.isEnabled = false
                    editTextPasswordField.error = "wrong"
                }
            }
            buttonRegister.setOnClickListener {
                buttonRegister.isEnabled = false
                register() }
        }
    }

    private fun validPassword(text: CharSequence?) =
        Regex("[a-z0-9?:!#$%&]{8,}").matches(text.toString())

    private fun validMail(text: CharSequence?) =
        Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()

    private fun register() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("name", binding.editTextEmailAddressField.text.toString())
        startActivity(intent)
        finish()
    }
}