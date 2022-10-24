package com.paypay.currency_converter

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

import org.junit.runner.RunWith

import org.robolectric.RobolectricTestRunner

import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

import com.paypay.currency_converter.di.commonModule
import com.paypay.currency_converter.di.platformModule
import com.paypay.currency_converter.domain.model.ConvertedRate
import com.paypay.currency_converter.domain.model.Currency
import com.paypay.currency_converter.domain.usecase.CurrencyUseCase

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class UseCaseTest : KoinTest {
    private val useCase: CurrencyUseCase by inject()
    private val dispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        startKoin {
            loadKoinModules(
                listOf(
                    platformModule(true),
                    commonModule()
                )
            )
        }
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun `The list of currencies must be of a certain size`() = runTest {
        val list = mutableListOf<Currency>()
        val job = launch {
            list.addAll(useCase.fetchCurrencies())
        }

        job.join()

        val actual = list.size
        val expected = 170
        assertEquals(expected, actual)
    }

    @Test
    fun `The list of converted rates must not be empty`() = runTest {
        val amount = 100.0
        val currency = "USD"

        val list = mutableListOf<ConvertedRate>()
        val job = launch {
            list.addAll(useCase.fetchConvertedRates(amount, currency))
        }

        job.join()

        val actual = list.size > 0
        val expected = true
        assertEquals(expected, actual)
    }
}