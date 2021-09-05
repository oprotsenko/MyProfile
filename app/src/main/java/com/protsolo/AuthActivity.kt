package com.protsolo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

import android.content.Intent
import android.widget.EditText
import androidx.core.widget.doOnTextChanged


class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        val emailData = findViewById<EditText>(R.id.editTextEmailAddressField)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        emailData.doOnTextChanged { text, start, before, count ->
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailData.toString()).matches())
                buttonRegister.isEnabled = true
        }
        setListeners()
    }

    private fun setListeners() {
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        buttonRegister.setOnClickListener { register() }
    }

    private fun register() {
        val intent = Intent(this, MainActivity::class.java)
        val emailData = findViewById<EditText>(R.id.editTextEmailAddressField)
        intent.putExtra("name", emailData.text.toString())
        startActivity(intent)
        finish()
    }
}