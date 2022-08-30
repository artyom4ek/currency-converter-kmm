package com.paypay.currency_converter.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.CoroutineScope

actual abstract class SharedViewModel actual constructor() : ViewModel() {
    actual val coroutineScope: CoroutineScope = viewModelScope

    actual override fun onCleared() {
        super.onCleared()
    }
}