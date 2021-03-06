package com.protsolo

import PreferenceStorage
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.protsolo.databinding.ActivityMainBinding
import java.lang.StringBuilder


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val preferencesStorage: PreferenceStorage = PreferenceStorage(this)
    val appPreferencesEmail = "email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        setName()
    }

    /**
     * Gets the email from the app base, calls the method to
     * parse it for the name and soname.
     */
    private fun setName() {
        val name = preferencesStorage.getString(appPreferencesEmail, "none")
        val parsedUserName: String? = parseName(name)
        binding.textViewUserName.text = parsedUserName
    }

    /**
     * Parse the obtained string to the user name.
     */
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
        binding.buttonEditProfile.setOnClickListener {
            startEditProfileActivity()
        }
    }

    private fun startEditProfileActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }


}