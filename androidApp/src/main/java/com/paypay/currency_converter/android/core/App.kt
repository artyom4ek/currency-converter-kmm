package com.paypay.currency_converter.android.core

import android.app.Application

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

import com.paypay.currency_converter.di.initKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
        }
    }
}