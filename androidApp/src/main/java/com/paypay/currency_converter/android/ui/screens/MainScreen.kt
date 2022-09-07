package com.paypay.currency_converter.android.ui.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
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
        Row {
            CurrencyAmountInput(localFocusManager)
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
        shape = RoundedCornerShape(percent = 10),
        singleLine = true,
        onValueChange = {
            textState.value = it
        },
        modifier = Modifier
            .fillMaxWidth(),
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

@Preview(showBackground = true)
@Composable
fun TextFieldPreview() {
    MainScreen()
}
