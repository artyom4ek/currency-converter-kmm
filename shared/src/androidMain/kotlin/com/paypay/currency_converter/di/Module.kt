package com.paypay.currency_converter.di

import java.util.*

import android.preference.PreferenceManager
import androidx.test.core.app.ApplicationProvider

import org.koin.core.module.Module
import org.koin.dsl.module

import io.ktor.client.engine.okhttp.*

import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

import com.paypay.currency_converter.db.CurrencyDatabase
import com.paypay.currency_converter.utils.Language

actual fun platformModule(): Module = module {
    single<Settings> {
        SharedPreferencesSettings(
            PreferenceManager.getDefaultSharedPreferences(get())
        )
    }
    factory<Language> { Locale.getDefault().language }
    single<SqlDriver> {
        AndroidSqliteDriver(
            CurrencyDatabase.Schema,
            get(),
            "currency.db"
        )
    }
    single {
        OkHttp.create()
    }
}