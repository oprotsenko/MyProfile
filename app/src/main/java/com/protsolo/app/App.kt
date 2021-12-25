package com.protsolo.app

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.protsolo.app.di.remoteModule
import com.protsolo.app.di.retrofitModule
import com.protsolo.app.di.useCasesModule
import com.protsolo.app.di.viewModelsModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(viewModelsModules, retrofitModule, useCasesModule, remoteModule))
        }
        Fresco.initialize(this)
    }
}