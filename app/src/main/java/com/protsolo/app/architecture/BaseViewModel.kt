package com.protsolo.app.architecture

import androidx.lifecycle.ViewModel
import com.protsolo.app.utils.PreferenceStorage

abstract class BaseViewModel : ViewModel() {
    protected val preferenceStorage: PreferenceStorage = PreferenceStorage()
}