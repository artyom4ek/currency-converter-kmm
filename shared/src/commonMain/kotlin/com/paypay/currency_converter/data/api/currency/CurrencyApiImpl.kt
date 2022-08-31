package com.paypay.currency_converter.data.api.currency

import io.ktor.client.*

import io.ktor.client.call.*
import io.ktor.client.request.*

import com.paypay.currency_converter.BuildKonfig
import com.paypay.currency_converter.data.entity.LatestRateDto

class CurrencyApiImpl constructor(private val client: HttpClient) : CurrencyApi {

    override suspend fun getLatestRates(): LatestRateDto =
        // Get APP_ID from secure space
        client.get("$BASE_URL$LATEST_ENDPOINT?app_id=${BuildKonfig.APP_ID}").body()

    companion object {
        private const val BASE_URL = "https://openexchangerates.org/api/"
        private const val LATEST_ENDPOINT = "latest.json"
    }
}