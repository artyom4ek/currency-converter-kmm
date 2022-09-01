package com.paypay.currency_converter.utils

import com.ionspin.kotlin.bignum.decimal.DecimalMode
import com.ionspin.kotlin.bignum.decimal.RoundingMode
import com.ionspin.kotlin.bignum.decimal.toBigDecimal

class CurrencyUtils {
    companion object {
        fun calculateRate(currentRate: Double, selectedRate: Double, amount: Double): String {
            val calculatedRate = (currentRate / selectedRate) * amount
            return calculatedRate.toBigDecimal(
                decimalMode = DecimalMode(4, RoundingMode.CEILING)
            ).toString()
        }
    }
}