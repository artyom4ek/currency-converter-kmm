package com.paypay.currency_converter.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

import com.paypay.currency_converter.data.api.currency.CurrencyApi
import com.paypay.currency_converter.data.fileSystem.LocalSettings
import com.paypay.currency_converter.db.CurrencyDatabase
import com.paypay.currency_converter.domain.model.*
import com.paypay.currency_converter.domain.repository.CurrencyRepository

class CurrencyRepositoryImpl constructor(
    private val dispatcher: CoroutineDispatcher,
    private val localSettings: LocalSettings,
    private val db: CurrencyDatabase,
    private val currencyApi: CurrencyApi,
) : CurrencyRepository {

    override suspend fun fetchCurrencies(): List<Currency> = withContext(dispatcher) {
        return@withContext listOf(Currency("UAH", "Ukrainian Hryvnia"))
    }

    override suspend fun fetchConvertedRates(
        enteredAmount: Double,
        selectedCurrency: String
    ): List<ConvertedRate> = withContext(dispatcher) {
        localSettings.latestUpdateTime
        db.currencyQueries.selectAll()
        currencyApi.getLatestRates()
        return@withContext emptyList()
    }
}