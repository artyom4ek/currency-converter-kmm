package com.paypay.currency_converter.android.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.paypay.currency_converter.android.R

@Composable
fun MainScreen() {
    val localFocusManager = LocalFocusManager.current
    
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    localFocusManager.clearFocus()
                })
            }

    ) {
        Text(
            text = stringResource(R.string.app_name),
            color = Color(0xFF414141),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            Arrangement.spacedBy(10.dp)
        ) {
            Box(Modifier.weight(1f)) {
                CurrencyAmountInput(localFocusManager)
            }
            Box(Modifier.weight(0.4f)) {
                CurrencyList {
                    Log.i("test", it)
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CurrencyAmountInput(localFocusManager: FocusManager) {
    val textState = remember { mutableStateOf(TextFieldValue()) }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = textState.value,
        singleLine = true,
        onValueChange = {
            textState.value = it
        },
        placeholder = {
            Text(text = stringResource(R.string.amount_placeholder))
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        keyboardActions = KeyboardActions(
            onNext = {
                keyboardController?.hide()
                localFocusManager.clearFocus()
            }
        ),
        trailingIcon = {
            IconButton(
                onClick = { textState.value = TextFieldValue("") },
                modifier = Modifier.size(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = stringResource(R.string.clear),
                )
            }
        },
    )
}

@Composable
fun CurrencyList(onClick: (currencyValue: String) -> Unit) {
    val currencies = listOf("USD", "UAH", "EUR", "JPY")
    val expanded = remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf(currencies[0]) }

    Box {
        Column {
            OutlinedTextField(
                value = (selectedItem.value),
                onValueChange = { },
                singleLine = true,
                trailingIcon = { Icon(Icons.Outlined.ArrowDropDown, null) },
                readOnly = true
            )
            DropdownMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(0.dp, 500.dp),
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
            ) {
                currencies.forEach { value ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            selectedItem.value = value
                            expanded.value = false
                            onClick(value)
                        },
                        content = {
                            Text(
                                text = (value),
                                modifier = Modifier
                                    .wrapContentWidth()
                            )
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    onClick = { expanded.value = !expanded.value }
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldPreview() {
    MainScreen()
}
