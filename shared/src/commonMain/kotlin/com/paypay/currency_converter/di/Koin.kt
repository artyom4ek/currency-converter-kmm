package com.paypay.currency_converter.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

// Called by Android
fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(platformModule(), commonModule())
}

// Called by iOS
fun initKoin() = initKoin {}