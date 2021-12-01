package com.protsolo.app

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.protsolo.app.architecture.modules.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(appModules))
        }
        Fresco.initialize(this)
    }
}