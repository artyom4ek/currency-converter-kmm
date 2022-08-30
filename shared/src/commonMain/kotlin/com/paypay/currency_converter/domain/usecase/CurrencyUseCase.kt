package com.paypay.currency_converter.domain.usecase

import com.paypay.currency_converter.domain.model.Currency
import com.paypay.currency_converter.domain.model.ConvertedRate
import com.paypay.currency_converter.domain.repository.CurrencyRepository

class CurrencyUseCase constructor(
    private val currencyRepository: CurrencyRepository
) {

    suspend fun fetchCurrencies(): List<Currency> {
        return currencyRepository.fetchCurrencies()
    }

    suspend fun fetchConvertedRates(enteredAmount: Double, currency: String): List<ConvertedRate> {
        return currencyRepository.fetchConvertedRates(enteredAmount, currency)
    }
}