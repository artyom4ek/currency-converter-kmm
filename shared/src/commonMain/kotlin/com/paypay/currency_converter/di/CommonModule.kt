package com.paypay.currency_converter.di

import kotlinx.coroutines.Dispatchers

import org.koin.core.module.Module
import org.koin.dsl.module

import com.russhwolf.settings.Settings

import com.paypay.currency_converter.data.api.currency.*
import com.paypay.currency_converter.data.api.provideHttpClient
import com.paypay.currency_converter.data.fileSystem.LocalSettings
import com.paypay.currency_converter.data.repository.CurrencyRepositoryImpl
import com.paypay.currency_converter.domain.repository.CurrencyRepository
import com.paypay.currency_converter.domain.usecase.CurrencyUseCase
import com.paypay.currency_converter.viewModel.CurrencyViewModel

fun commonModule() = module {
    factory { Settings() }
    single { LocalSettings(get()) }
    factory { provideHttpClient() }
    single<CurrencyApi> { CurrencyApiImpl(get()) }
    single<CurrencyRepository> { CurrencyRepositoryImpl(Dispatchers.Default, get(), get()) }
    factory { CurrencyUseCase(get()) }
    single { CurrencyViewModel(get()) }
}

expect fun platformModule(): Module
