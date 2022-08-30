package com.paypay.currency_converter.viewModel

import kotlinx.coroutines.CoroutineScope

expect abstract class SharedViewModel() {
    val coroutineScope: CoroutineScope
    protected open fun onCleared()
}