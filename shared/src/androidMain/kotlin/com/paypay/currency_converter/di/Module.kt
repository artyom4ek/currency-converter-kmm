package com.paypay.currency_converter.di

import org.koin.core.module.Module
import org.koin.dsl.module

import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

import com.paypay.currency_converter.db.CurrencyDatabase

actual fun platformModule(): Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            CurrencyDatabase.Schema,
            get(),
            "currency.db"
        )
    }
}