package com.paypay.currency_converter.data.api.currency

import kotlinx.serialization.json.Json

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*

import com.paypay.currency_converter.BuildKonfig
import com.paypay.currency_converter.data.entity.LatestRateDto

class CurrencyApiImpl constructor(engine: HttpClientEngine) : CurrencyApi {
    private val client = HttpClient(engine)
    {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        expectSuccess = true
    }

    override suspend fun getLatestRates(): LatestRateDto =
        // Get APP_ID from secure space
        client.get("$BASE_URL$LATEST_ENDPOINT?app_id=${BuildKonfig.APP_ID}").body()

    companion object {
        private const val BASE_URL = "https://openexchangerates.org/api/"
        private const val LATEST_ENDPOINT = "latest.json"
    }
}