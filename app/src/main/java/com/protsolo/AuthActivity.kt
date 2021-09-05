package com.protsolo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged


class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        val emailData = findViewById<EditText>(R.id.editTextEmailAddressField)
        emailData.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailData.text.toString()).matches())
                    emailData.error = "Invalid email"
            }


            override fun afterTextChanged(s: Editable?) {
            }

        })
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