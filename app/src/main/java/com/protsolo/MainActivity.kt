package com.protsolo

import PreferenceStorage
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.protsolo.databinding.ActivityMainBinding
import com.protsolo.utils.Constants


class MainActivity : AppCompatActivity() {

    private val preferencesStorage: PreferenceStorage = PreferenceStorage(this)

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        var photo = binding.imageViewMainProfilePhoto
        Glide.with(photo.context)
            .load("https://scontent.fiev17-2.fna.fbcdn.net/v/t1.6435-9/242289197_4285719178131249_2304531192454725485_n.jpg?_nc_cat=101&ccb=1-5&_nc_sid=09cbfe&_nc_ohc=Oxz1lcqMvCkAX8NjPEn&_nc_ht=scontent.fiev17-2.fna&oh=b20254fb6d57b2202f29c63d329da38f&oe=616E2EA4")
            .into(photo)
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