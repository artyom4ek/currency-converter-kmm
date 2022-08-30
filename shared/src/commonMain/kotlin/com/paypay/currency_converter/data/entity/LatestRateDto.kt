package com.paypay.currency_converter.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LatestRateDto(
    @SerialName("rates") val rates: Map<String, Double>?
)