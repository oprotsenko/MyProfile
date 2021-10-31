package com.protsolo.ui.mainPage

import androidx.lifecycle.ViewModel
import com.protsolo.utils.Constants

class MainPageViewModel : ViewModel() {

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
}