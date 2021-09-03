package com.protsolo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.lang.StringBuilder


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
        setObtainedData()
    }

    private fun setObtainedData() {
        val name = intent.getStringExtra("name")
        val parsedUserName: String? = parseName(name)
        val userName = findViewById<TextView>(R.id.textViewUserName)
        userName.text = parsedUserName
    }

    private fun parseName(name: String?): String? {
        val res = StringBuilder()
        if (name == null)
            return null
        for (c in name.indices) {
            when (name[c]) {
                '.' -> res.append(" ")
                '@' -> break
                else -> if (c == 0 || res[c - 1] == ' ') res.append(name[c].uppercase()) else
                    res.append(name[c])
            }
        }
        Log.d("TAG", res.toString())
        return res.toString()
    }

    private fun setListeners() {
        val editProfile = findViewById<Button>(R.id.buttonEditProfile)
        editProfile.setOnClickListener {
            editProfile()
        }
    }

    private fun editProfile() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }


}