package com.paypay.currency_converter.viewModel

import kotlinx.coroutines.*

actual abstract class SharedViewModel {
    actual val coroutineScope = MainScope()

    protected actual open fun onCleared() {

    }

    fun clear() {
        onCleared()
        coroutineScope.cancel()
    }
}