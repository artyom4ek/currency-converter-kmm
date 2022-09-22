package com.paypay.currency_converter.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import kotlinx.coroutines.launch

import org.koin.androidx.compose.get

import com.paypay.currency_converter.android.R
import com.paypay.currency_converter.domain.model.ConvertedRate
import com.paypay.currency_converter.domain.model.Currency
import com.paypay.currency_converter.viewModel.CurrencyViewModel

@Composable
fun MainScreen(
    viewModel: CurrencyViewModel = get()
) {
    val state = viewModel.state.collectAsState(CurrencyViewModel.State.INIT).value
    val currencies = viewModel.currencies.collectAsState().value
    val convertedRates = viewModel.convertedRates.collectAsState().value

    val currencyState = remember { mutableStateOf("") }
    val amountState = remember { mutableStateOf(TextFieldValue()) }

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
                CurrencyAmountInput(localFocusManager, amountState) {
                    viewModel.fetchConvertedRates(amountState.value.text, currencyState.value)
                }
            }
            Box(Modifier.weight(0.4f)) {
                currencies?.let {
                    if (currencyState.value.isEmpty()) {
                        currencyState.value = currencies[0].name
                    }

                    CurrencyList(it) { selectedItem ->
                        currencyState.value = selectedItem

                        viewModel.fetchConvertedRates(amountState.value.text, currencyState.value)
                    }
                }
            }
        }
        Content(state, convertedRates)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CurrencyAmountInput(
    localFocusManager: FocusManager,
    amountState: MutableState<TextFieldValue>,
    onDone: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = amountState.value,
        singleLine = true,
        onValueChange = {
            amountState.value = it
        },
        modifier = Modifier.background(Color.White),
        placeholder = {
            Text(text = stringResource(R.string.amount_placeholder))
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                localFocusManager.clearFocus()
                onDone.invoke()
            }
        ),
        trailingIcon = {
            IconButton(
                onClick = { amountState.value = TextFieldValue("") },
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
fun CurrencyList(currencies: List<Currency>, onClick: (currencyValue: String) -> Unit) {
    val expanded = remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf(currencies[0]) }

    Box {
        Column {
            OutlinedTextField(
                value = (selectedItem.value.name),
                onValueChange = { },
                singleLine = true,
                trailingIcon = { Icon(Icons.Outlined.ArrowDropDown, null) },
                readOnly = true,
                modifier = Modifier.background(Color.White)
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
                            onClick(value.name)
                        },
                        content = {
                            Text(
                                text = stringResource(
                                    R.string.currency_full_name,
                                    value.name,
                                    value.description
                                ),
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

@Composable
fun Content(state: CurrencyViewModel.State, convertedRate: List<ConvertedRate>) {
    when (state) {
        CurrencyViewModel.State.INIT -> Init()
        CurrencyViewModel.State.LOADING -> Loading()
        CurrencyViewModel.State.SUCCESS -> ConvertedRateList(convertedRate)
        CurrencyViewModel.State.EMPTY -> Empty()
        CurrencyViewModel.State.ERROR -> Error()
    }
}

@Composable
private fun Init() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.info))
    }
}

@Composable
private fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun Empty() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.empty_list))
    }
}

@Composable
private fun Error() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val error = stringResource(R.string.rates_loading_error)
    scope.launch {
        snackbarHostState.showSnackbar(error)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SnackbarHost(hostState = snackbarHostState) {
            Snackbar(
                snackbarData = it,
                backgroundColor = Color.Red,
                contentColor = Color.White
            )
        }
    }
}

@Composable
fun ConvertedRateList(rates: List<ConvertedRate>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 16.dp
        ),
        content = {
            items(rates.size) { index ->
                Card(
                    backgroundColor = Color.White,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxSize(),
                    elevation = 8.dp,
                ) {
                    Column(
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = rates[index].name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFF2E2E2E),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth()
                        )
                        Text(
                            text = rates[index].value,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF1C7416),
                            modifier = Modifier.padding(4.dp),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TextFieldPreview() {
    MainScreen()
}