package com.paypay.currency_converter.domain.model

data class Currency(val name: String, val description: String) {
    override fun toString(): String {
        return name
    }
}
