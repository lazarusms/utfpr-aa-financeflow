package com.utfpr.financeflow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.utfpr.financeflow.model.TransactionType
import com.utfpr.financeflow.ui.components.DatePickerField
import com.utfpr.financeflow.viewmodel.TransactionViewModel
import com.utfpr.financeflow.ui.components.TransactionDropdown
import com.utfpr.financeflow.ui.components.TransactionInputField
import com.utfpr.financeflow.ui.components.TransactionValueInput
import com.utfpr.financeflow.ui.theme.FinanceFlowTheme

@Composable
fun AddTransactionScreen(
    viewModel: TransactionViewModel = viewModel()
) {
    val valueFocusRequester = remember { FocusRequester() }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.message) {
        viewModel.message?.let { msg ->
            kotlinx.coroutines.withTimeoutOrNull(2000) {//2seg pq usar o short ainda eh mt demorado
                snackbarHostState.showSnackbar(
                    message = msg,
                    duration = SnackbarDuration.Indefinite
                )
            }
            viewModel.clearMessage()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Adicionar nova transação:",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            TransactionValueInput(
                label = "Valor",
                value = viewModel.amount,
                onValueChange = { viewModel.updateAmount(it) }
            )

            TransactionDropdown(
                label = "Tipo",
                value = viewModel.type,
                options = listOf(TransactionType.RECEITA.description, TransactionType.DESPESA.description),
                onValueChange = { viewModel.type = it },
                modifier = Modifier.focusRequester(valueFocusRequester)
            )

            DatePickerField(
                label = "Data do lançamento",
                date = viewModel.date,
                onDateSelected = { viewModel.date = it }
            )

            TransactionInputField(
                label = "Descrição:",
                value = viewModel.description,
                onValueChange = { viewModel.description = it }
            )
        }

        TransactionActionButtons(
            onSave = {
                viewModel.saveTransaction()
                valueFocusRequester.requestFocus()
            },
            onClear = {
                viewModel.clearFields()
                valueFocusRequester.requestFocus()
            }
        )

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.padding(bottom = 8.dp),
        ) { data ->
            Snackbar(
                snackbarData = data,
                containerColor = if (viewModel.isError)
                    MaterialTheme.colorScheme.error
                else
                    MaterialTheme.colorScheme.primary,
                contentColor = if (viewModel.isError)
                    MaterialTheme.colorScheme.onError
                else
                    MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun TransactionActionButtons(onSave: () -> Unit = {}, onClear: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = onSave,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(text = "Salvar")
        }

        Button(
            onClick = onClear,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(text = "Limpar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionPreview() {
    FinanceFlowTheme {
        AddTransactionScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionActionButtonsPreview() {
    FinanceFlowTheme {
        TransactionActionButtons()
    }
}