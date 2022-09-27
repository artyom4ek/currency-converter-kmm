package com.paypay.currency_converter.viewModel

import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

import com.paypay.currency_converter.domain.model.Currency
import com.paypay.currency_converter.domain.model.ConvertedRate
import com.paypay.currency_converter.domain.usecase.CurrencyUseCase

class CurrencyViewModel : SharedViewModel(), KoinComponent {
    private val currencyUseCase: CurrencyUseCase by inject()

    private val _currencies = MutableStateFlow<List<Currency>?>(null)
    val currencies: StateFlow<List<Currency>?> = _currencies

    private val _convertedRates = MutableStateFlow<List<ConvertedRate>>(emptyList())
    val convertedRates: StateFlow<List<ConvertedRate>> = _convertedRates

    private val _state = MutableSharedFlow<State>(0)
    val state: SharedFlow<State> = _state

    init {
        initCurrencies()
    }

    private fun initCurrencies() {
        coroutineScope.launch {
            _currencies.emit(currencyUseCase.fetchCurrencies())
        }
    }

    /**
     * Convenience method for iOS observing the [currencies]
     */
    @Suppress("unused")
    fun observeCurrencies(onChange: (List<Currency>?) -> Unit) {
        currencies.onEach {
            onChange(it)
        }.launchIn(coroutineScope)
    }

    fun fetchConvertedRates(enteredAmount: String, selectedCurrency: String) {
        coroutineScope.launch {
            try {
                if (enteredAmount.isEmpty()) {
                    _state.emit(State.ERROR)
                    return@launch
                }

                val parsedAmount = enteredAmount.toDouble()
                if (parsedAmount.compareTo(0) == 0) {
                    _state.emit(State.ERROR)
                    return@launch
                }

                _state.emit(State.LOADING)

                val convertedRates =
                    currencyUseCase.fetchConvertedRates(parsedAmount, selectedCurrency)
                if (convertedRates.isNotEmpty()) {
                    _convertedRates.emit(convertedRates)
                    _state.emit(State.SUCCESS)
                } else {
                    _state.emit(State.EMPTY)
                }
            } catch (t: Throwable) {
                println(t)
                _state.emit(State.ERROR)
            }
        }
    }

    enum class State {
        NORMAL,
        LOADING,
        SUCCESS,
        EMPTY,
        ERROR
    }
}