package com.example.allocinoche

import android.app.Application
import com.example.allocinoche.di.alloCinocheApp
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AlloCinocheApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        startKoin {
            androidLogger()
            androidContext(this@AlloCinocheApplication)
            modules(alloCinocheApp)
        }
    }
}