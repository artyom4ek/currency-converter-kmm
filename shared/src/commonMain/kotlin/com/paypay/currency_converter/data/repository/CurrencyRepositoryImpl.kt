package com.paypay.currency_converter.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

import com.paypay.currency_converter.data.api.currency.CurrencyApi
import com.paypay.currency_converter.data.entity.LatestRateDto
import com.paypay.currency_converter.data.fileSystem.LocalSettings
import com.paypay.currency_converter.data.mapper.Mapper
import com.paypay.currency_converter.db.CurrencyDatabase
import com.paypay.currency_converter.domain.model.*
import com.paypay.currency_converter.domain.repository.CurrencyRepository
import com.paypay.currency_converter.utils.ConstUtils
import com.paypay.currencyconverter.db.RateEntity

class CurrencyRepositoryImpl constructor(
    private val dispatcher: CoroutineDispatcher,
    private val localSettings: LocalSettings,
    private val db: CurrencyDatabase,
    private val mapper: Mapper,
    private val currencyApi: CurrencyApi,
) : CurrencyRepository {

    override suspend fun fetchCurrencies(): List<Currency> = withContext(dispatcher) {
        return@withContext listOf(Currency("UAH", "Ukrainian Hryvnia"))
    }

    override suspend fun fetchConvertedRates(
        enteredAmount: Double,
        selectedCurrency: String
    ): List<ConvertedRate> = withContext(dispatcher) {
        val currencyQueries = db.currencyQueries

        val currentTime = Clock.System.now().toEpochMilliseconds()
        val latestUpdateTime = localSettings.latestUpdateTime

        val rateEntities: List<RateEntity>

        /*
         * Uploading updated data if the user logged into
         * the application for the first time or every 30 minutes
         */
        if (latestUpdateTime == 0L || currentTime - latestUpdateTime > ConstUtils.DATA_UPDATE_TIMEOUT) {
            val latestRatesDto: LatestRateDto

            try {
                latestRatesDto = currencyApi.getLatestRates()

            } catch (t: Throwable) {
                // If there are network problems, used cached data
                rateEntities = currencyQueries.getRates().executeAsList()
                return@withContext mapper.toConvertedRates(
                    rateEntities,
                    enteredAmount,
                    selectedCurrency
                )
            }

            rateEntities = mapper.toRateEntities(latestRatesDto)

            if (rateEntities.isNotEmpty()) {
                currencyQueries.deleteRates()
                rateEntities.forEach {
                    currencyQueries.insertRate(RateEntity(it.name, it.currentValue))
                }

                /*
                 * Update data adding time only when all previous
                 * operations have been successfully completed
                 */
                localSettings.latestUpdateTime = currentTime
            }
        } else {
            rateEntities = currencyQueries.getRates().executeAsList()
        }

        return@withContext mapper.toConvertedRates(rateEntities, enteredAmount, selectedCurrency)
    }
}