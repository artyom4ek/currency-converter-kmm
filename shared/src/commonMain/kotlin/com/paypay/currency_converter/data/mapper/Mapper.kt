package com.paypay.currency_converter.data.mapper

import com.paypay.currency_converter.data.entity.LatestRateDto
import com.paypay.currency_converter.domain.model.ConvertedRate
import com.paypay.currency_converter.utils.CurrencyUtils
import com.paypay.currencyconverter.db.RateEntity

class Mapper {

    fun toRateEntities(latestRatesDto: LatestRateDto): List<RateEntity> {
        if (latestRatesDto.rates.isNullOrEmpty()) {
            return emptyList()
        }

        return latestRatesDto.rates.map {
            RateEntity(it.key, it.value)
        }
    }

    fun toConvertedRates(
        rateEntities: List<RateEntity>?,
        enteredAmount: Double,
        selectedCurrencyName: String,
    ): List<ConvertedRate> {
        if (rateEntities.isNullOrEmpty()) return emptyList()

        val selectedCurrencyValue =
            rateEntities.find { rateEntity -> rateEntity.name == selectedCurrencyName }!!.currentValue

        val convertedRates = mutableListOf<ConvertedRate>()
        rateEntities.forEach {
            val currentCurrencyName = it.name
            val currentCurrencyValue = it.currentValue

            // Don't display the selected currency in the Converted list
            if (selectedCurrencyName == currentCurrencyName) return@forEach

            /*
             * To get the value of other currencies to the current currency,
             * need to divide the value of the current currency by all the others
             * and multiply to entered value
             */
            val convertedRate = CurrencyUtils.calculateRate(
                currentCurrencyValue,
                selectedCurrencyValue,
                enteredAmount
            )

            convertedRates.add(ConvertedRate(currentCurrencyName, convertedRate))
        }

        return convertedRates
    }
}