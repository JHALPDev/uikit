package com.taycode.uikit

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

abstract class UiKitApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}