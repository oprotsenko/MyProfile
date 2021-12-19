package com.protsolo.ui.main.viewPager.authorization.profile

import androidx.lifecycle.MutableLiveData
import com.protsolo.app.base.BaseViewModel
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.NameParser
import com.protsolo.app.utils.PreferenceStorage

class ProfileViewModel(preferenceStorage: PreferenceStorage) : BaseViewModel() {

    val userName by lazy { MutableLiveData<String>() }

    private val nameParser by lazy { NameParser() }

    init {
        userName.value =
            nameParser.parseName(preferenceStorage.getString(Constants.PREFERENCE_EMAIL_KEY))
    }
}