package com.paypay.currency_converter.di

import kotlinx.coroutines.Dispatchers

import org.koin.core.module.Module
import org.koin.dsl.module

import com.russhwolf.settings.Settings

import com.paypay.currency_converter.data.api.currency.*
import com.paypay.currency_converter.data.api.provideHttpClient
import com.paypay.currency_converter.data.fileSystem.LocalSettings
import com.paypay.currency_converter.data.mapper.Mapper
import com.paypay.currency_converter.data.repository.CurrencyRepositoryImpl
import com.paypay.currency_converter.db.CurrencyDatabase
import com.paypay.currency_converter.domain.repository.CurrencyRepository
import com.paypay.currency_converter.domain.usecase.CurrencyUseCase
import com.paypay.currency_converter.viewModel.CurrencyViewModel

fun commonModule() = module {
    factory { Settings() }
    factory { Mapper() }
    single { LocalSettings(get()) }
    factory { provideHttpClient() }
    single<CurrencyApi> { CurrencyApiImpl(get()) }
    single { CurrencyDatabase(get()) }
    single { get<CurrencyDatabase>().currencyQueries }
    single<CurrencyRepository> {
        CurrencyRepositoryImpl(
            Dispatchers.Default,
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    single { CurrencyUseCase(get()) }
    single { CurrencyViewModel(get()) }
}

expect fun platformModule(): Module
