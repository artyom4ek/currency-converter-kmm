package com.paypay.currency_converter

import kotlin.test.*

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

import com.paypay.currency_converter.data.mapper.Mapper
import com.paypay.currency_converter.domain.model.ConvertedRate
import com.paypay.currency_converter.utils.CurrencyUtils
import com.paypay.currencyconverter.db.RateEntity

class CommonTest : KoinTest {
    private val mapper: Mapper by inject()

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                module {
                    single { Mapper() }
                }
            )
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `Return the correct converted rate given the correct input data`() {
        val usdRate = 1.0
        val uahRate = 36.744719
        val amount = 100.0
        val expected = "2.7215"
        val actual = CurrencyUtils.calculateRate(usdRate, uahRate, amount)
        assertEquals(expected, actual)
    }

    @Test
    fun `Return an empty list if the input list is empty`() {
        val entities = emptyList<RateEntity>()
        val amount = 100.0
        val currency = "USD"
        val expected = 0
        val actual = mapper.toConvertedRates(entities, amount, currency).size
        assertEquals(expected, actual)
    }

    @Test
    fun `Return the correct number of returned list elements if valid input parameters are given`() {
        val entities = listOf(
            RateEntity("JPY", 136.8945),
            RateEntity("UAH", 36.744719),
            RateEntity("EUR", 0.996709),
            RateEntity("USD", 1.0),
            RateEntity("GBP", 0.846665),
        )
        val amount = 100.0
        val currency = "USD"
        val expected = 4
        val actual = mapper.toConvertedRates(entities, amount, currency)
        assertEquals(expected, actual.size)
    }

    @Test
    fun `Return a list of converted rates if valid input parameters are given`() {
        val entities = listOf(
            RateEntity("JPY", 136.8945),
            RateEntity("UAH", 36.744719),
            RateEntity("EUR", 0.996709),
            RateEntity("USD", 1.0),
            RateEntity("GBP", 0.846665),
        )
        val amount = 100.0
        val currency = "USD"
        val expected = listOf(
            ConvertedRate("JPY", "13689.45"),
            ConvertedRate("UAH", "3674.472"),
            ConvertedRate("EUR", "99.6709"),
            ConvertedRate("GBP", "84.6665"),
        )
        val actual = mapper.toConvertedRates(entities, amount, currency)
        assertContentEquals(expected, actual)
    }
}