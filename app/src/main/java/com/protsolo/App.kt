package com.protsolo

import android.annotation.SuppressLint
import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.protsolo.utils.PreferenceStorage

@SuppressLint("StaticFieldLeak")
private lateinit var preferencesStorage: PreferenceStorage

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        preferencesStorage = PreferenceStorage(this)
    }

    fun getStoragePreferences(): PreferenceStorage {
        return preferencesStorage
    }
}