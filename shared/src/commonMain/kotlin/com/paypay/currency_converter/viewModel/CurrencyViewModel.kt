package com.paypay.currency_converter.viewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

import com.paypay.currency_converter.domain.model.Currency
import com.paypay.currency_converter.domain.model.ConvertedRate
import com.paypay.currency_converter.domain.usecase.CurrencyUseCase

class CurrencyViewModel constructor(
    private val currencyUseCase: CurrencyUseCase
) : SharedViewModel() {
    private val _currencies = MutableStateFlow<List<Currency>?>(null)
    val currencies: StateFlow<List<Currency>?> = _currencies

    private val _convertedRates = MutableSharedFlow<Result<List<ConvertedRate>>>(1)
    val convertedRates: SharedFlow<Result<List<ConvertedRate>>> = _convertedRates

    init {
        initCurrencies()
    }

    private fun initCurrencies() {
        coroutineScope.launch {
            _currencies.emit(currencyUseCase.fetchCurrencies())
        }
    }

    fun fetchConvertedRates(enteredAmount: String, selectedCurrency: String) {
        coroutineScope.launch {

        }
    }
}