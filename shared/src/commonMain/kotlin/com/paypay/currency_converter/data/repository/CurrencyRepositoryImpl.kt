package com.paypay.currency_converter.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

import com.paypay.currency_converter.data.api.currency.CurrencyApi
import com.paypay.currency_converter.domain.model.ConvertedRate
import com.paypay.currency_converter.domain.model.Currency
import com.paypay.currency_converter.domain.repository.CurrencyRepository

class CurrencyRepositoryImpl constructor(
    private val dispatcher: CoroutineDispatcher,
    private val currencyApi: CurrencyApi,
) : CurrencyRepository {

    override suspend fun fetchCurrencies(): List<Currency> = withContext(dispatcher) {
        return@withContext listOf(Currency("UAH", "Ukrainian Hryvnia"))
    }

    override suspend fun fetchConvertedRates(
        enteredAmount: Double,
        selectedCurrency: String
    ): List<ConvertedRate> = withContext(dispatcher) {
        currencyApi.getLatestRates()
        return@withContext emptyList()
    }
}