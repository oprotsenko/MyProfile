package com.protsolo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

import android.content.Intent


class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        setListeners()
    }

    private fun setListeners() {
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        buttonRegister.setOnClickListener {
        sendMessage()
        }
    }

    private fun sendMessage() {
        val intent = Intent(this, MainActivity::class.java)
        val message = "name"
        intent.putExtra("message", message)
        startActivity(intent)
        finish()
    }
}