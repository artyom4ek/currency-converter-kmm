package com.paypay.currency_converter.di

import org.koin.core.module.Module
import org.koin.dsl.module

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

import com.paypay.currency_converter.db.CurrencyDatabase

actual fun platformModule(): Module = module {
    single<SqlDriver> { NativeSqliteDriver(CurrencyDatabase.Schema, "currency.db") }
}