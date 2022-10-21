package com.paypay.currency_converter

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import kotlin.test.*

import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

import com.paypay.currency_converter.di.initKoin
import com.paypay.currency_converter.viewModel.CurrencyViewModel

/**
 * Demonstration of the basic functions of validating the input field for the amount of currency.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelTest : KoinTest {
    private val viewModel: CurrencyViewModel by inject()
    private val dispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        initKoin()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun `Amount field must be greater than 0`() = runTest {
        val amount = "0.0"
        val currency = "USD"

        validateBadInputs(this, amount, currency)
    }

    @Test
    fun `Amount field cannot be empty`() = runTest {
        val amount = ""
        val currency = "USD"

        validateBadInputs(this, amount, currency)
    }

    @Test
    fun `The entered value must be in the correct format`() = runTest {
        val amount = "."
        val currency = "USD"

        validateBadInputs(this, amount, currency)
    }

    private suspend fun validateBadInputs(testScope: TestScope, amount: String, currency: String) {
        val actual = mutableListOf<CurrencyViewModel.State>()
        val job = testScope.launch {
            viewModel.state.toList(actual)
        }

        viewModel.fetchConvertedRates(amount, currency)

        delay(10)

        val expected = CurrencyViewModel.State.ERROR
        job.cancel()
        assertContains(actual, expected)
    }
}