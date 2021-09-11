package com.protsolo

import PreferenceStorage
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.protsolo.databinding.ActivityMainBinding
import com.protsolo.utils.Constants
import java.lang.StringBuilder


class MainActivity : AppCompatActivity() {

    private val preferencesStorage: PreferenceStorage = PreferenceStorage(this)

    private lateinit var binding: ActivityMainBinding

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
        val name = preferencesStorage.getString(Constants.PREFERENCE_EMAIL_KEY)
        val parsedUserName: String = parseName(name)
        binding.textViewMainUserName.text = parsedUserName
    }

    /**
     * Parse the obtained string to the user name.
     */
    private fun parseName(name: String?): String {
        val res = StringBuilder()
        if (name.isNullOrEmpty())
            return Constants.DEFAULT_NAME
        for (c in name.indices) {
            when (name[c]) {
                '.' -> res.append(" ")
                '@' -> break
                else -> if (c == 0 || res[c - 1] == ' ') res.append(name[c].uppercase()) else
                    res.append(name[c])
            }
        }
        return res.toString()
    }

    private fun setListeners() {
        binding.buttonMainEditProfile.setOnClickListener {
            startEditProfileActivity()
        }
    }

    private fun startEditProfileActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}