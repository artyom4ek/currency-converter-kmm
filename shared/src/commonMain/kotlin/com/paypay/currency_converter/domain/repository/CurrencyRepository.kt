package com.paypay.currency_converter.domain.repository

import com.paypay.currency_converter.domain.model.Currency
import com.paypay.currency_converter.domain.model.ConvertedRate

interface CurrencyRepository {

    /**
     * Get a list of currencies with their description
     */
    suspend fun fetchCurrencies(): List<Currency>

    /**
     * Get converted rates from local and remote sources
     */
    suspend fun fetchConvertedRates(
        enteredAmount: Double,
        selectedCurrency: String
    ): List<ConvertedRate>
}