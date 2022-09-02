package com.paypay.currency_converter.di

import java.util.*

import org.koin.core.module.Module
import org.koin.dsl.module

import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

import com.paypay.currency_converter.db.CurrencyDatabase
import com.paypay.currency_converter.utils.Language

actual fun platformModule(): Module = module {
    factory<Language> { Locale.getDefault().language }
    single<SqlDriver> {
        AndroidSqliteDriver(
            CurrencyDatabase.Schema,
            get(),
            "currency.db"
        )
    }
}