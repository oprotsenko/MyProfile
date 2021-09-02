package com.protsolo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.TextView



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()

        val message = intent.getStringExtra("message")
        val messageText = findViewById<TextView>(R.id.textViewUserName)
        messageText.text = message
    }

    private fun setListeners() {
//        val messageText = findViewById<TextView>(R.id.messageText)
    }


}