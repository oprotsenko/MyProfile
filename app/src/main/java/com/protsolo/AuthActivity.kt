package com.protsolo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

import android.content.Intent
import android.widget.EditText
import android.widget.Toast


class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        setListeners()
    }

    private fun setListeners() {
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        buttonRegister.setOnClickListener { register() }
    }

    private fun register() {
        val intent = Intent(this, MainActivity::class.java)
        val emailData = findViewById<EditText>(R.id.editTextEmailAddressField)
        if (!validator(emailData)) {
            Toast.makeText(this, "e-mail not valid", Toast.LENGTH_SHORT).show()
            setListeners()
        }
        intent.putExtra("name", emailData.text.toString())
        startActivity(intent)
        finish()
    }

    private fun validator(emailData: EditText): Boolean {
        if (emailData.text.isEmpty() || !emailData.text.matches(Regex("^[a-zA-Z0-9]+@[a-zA-Z0-9]+(.[a-zA-Z]{2,})$")))
            return false
        return true
    }
}