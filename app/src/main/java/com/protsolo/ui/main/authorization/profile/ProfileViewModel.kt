package com.protsolo.ui.main.authorization.profile

import androidx.lifecycle.ViewModel
import com.protsolo.app.App
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.SingleLiveEvent

class ProfileViewModel : ViewModel() {

    private val preferenceStorage = App.preferencesStorage

    val userName by lazy { SingleLiveEvent<String>() }

    init {
        parseName()
    }

    private fun parseName() {
        val email = preferenceStorage.getString(Constants.PREFERENCE_EMAIL_KEY)
        val firstName = email?.substring(0, email.indexOf('.')) ?: ""
        val lastName = email?.substring(email.indexOf('.'), email.indexOf('@'))
            ?.replace('.', ' ')

        userName.value = firstName.replaceFirst(firstName[0], firstName[0].uppercaseChar(), false) +
                lastName?.replaceFirst(lastName[1], lastName[1].uppercaseChar(), false)
    }
}