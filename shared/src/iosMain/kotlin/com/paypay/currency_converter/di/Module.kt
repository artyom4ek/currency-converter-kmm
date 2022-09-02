package com.paypay.currency_converter.di

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

import org.koin.core.module.Module
import org.koin.dsl.module

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

import com.paypay.currency_converter.db.CurrencyDatabase
import com.paypay.currency_converter.utils.Language

actual fun platformModule(): Module = module {
    factory<Language> { NSLocale.currentLocale.languageCode }
    single<SqlDriver> { NativeSqliteDriver(CurrencyDatabase.Schema, "currency.db") }
}