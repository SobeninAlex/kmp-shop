package org.example.kmp_shop

import android.app.Application
import org.example.kmp_shop.di.initKoin
import org.koin.android.ext.koin.androidContext

class AndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@AndroidApp)
        }
    }
}