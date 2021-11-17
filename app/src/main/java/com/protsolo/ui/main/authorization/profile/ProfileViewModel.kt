package com.protsolo.ui.main.authorization.profile

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.protsolo.app.App
import com.protsolo.app.utils.Constants

class ProfileViewModel : ViewModel() {

    private val preferenceStorage = App.preferencesStorage

    /**
     * Parse the obtained string to the user name.
     */
    fun parseName(name: String?): String {
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

    fun getName(arguments: Bundle?): String {
        var name = if (Constants.NAV_GRAPH) {
            arguments?.let { ProfileFragmentArgs.fromBundle(it).email }
        } else {
            arguments?.getString(Constants.PREFERENCE_EMAIL_KEY)
        }
        if (name.isNullOrEmpty()) {
            name = preferenceStorage.getString(Constants.PREFERENCE_EMAIL_KEY)
        }
        return when(name) {
            null -> "User name"
            else -> name
        }
    }
}