package com.protsolo.ui.main.authorization.profile

import androidx.lifecycle.MutableLiveData
import com.protsolo.app.base.BaseViewModel
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.PreferenceStorage

class ProfileViewModel(private val preferenceStorage: PreferenceStorage) : BaseViewModel() {

    val userName by lazy { MutableLiveData<String>() }

    init {
        parseName()
    }

    private fun parseName() {
        val email = preferenceStorage.getString(Constants.PREFERENCE_EMAIL_KEY)
        val name = email?.substring(0, email.indexOf('@'))
        userName.value =
            if (name?.contains('.', false) == true) {
                val index = name.indexOf('.') + 1
                name.replace('.', ' ').replaceFirst(name[0], name[0].uppercaseChar())
                    .replace(name[index], name[index].uppercaseChar(), true)
            } else {
                name?.replaceFirst(name[0], name[0].uppercaseChar())
            }
    }
}