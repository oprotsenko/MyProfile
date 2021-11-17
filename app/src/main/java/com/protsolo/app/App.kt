package com.protsolo.app

import android.annotation.SuppressLint
import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.protsolo.app.utils.PreferenceStorage

@SuppressLint("StaticFieldLeak")
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        preferencesStorage = PreferenceStorage(this)
    }

    companion object {
        var isFirstLogin = true
        lateinit var preferencesStorage: PreferenceStorage
    }
}