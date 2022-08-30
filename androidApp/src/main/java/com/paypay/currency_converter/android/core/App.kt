package com.paypay.currency_converter.android.core

import android.app.Application

import com.paypay.currency_converter.di.initKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}