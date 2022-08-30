package com.paypay.currency_converter.data.api.currency

import com.paypay.currency_converter.data.entity.LatestRateDto

class CurrencyApiImpl : CurrencyApi {

    override suspend fun getLatestRates(): LatestRateDto {
        return LatestRateDto(null)
    }
}