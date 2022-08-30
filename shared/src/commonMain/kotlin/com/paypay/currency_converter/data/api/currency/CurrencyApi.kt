package com.paypay.currency_converter.data.api.currency

import com.paypay.currency_converter.data.entity.LatestRateDto

interface CurrencyApi {

    /**
     * Get the latest exchange rates available from the Open Exchange Rates API
     */
    suspend fun getLatestRates(): LatestRateDto
}