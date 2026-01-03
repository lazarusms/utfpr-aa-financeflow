package com.utfpr.financeflow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.utfpr.financeflow.ui.components.DatePickerField
import com.utfpr.financeflow.viewmodel.TransactionViewModel
import com.utfpr.financeflow.ui.components.TransactionDropdown
import com.utfpr.financeflow.ui.components.TransactionInputField
import com.utfpr.financeflow.ui.components.TransactionValueInput
import com.utfpr.financeflow.ui.theme.FinanceFlowTheme
import java.time.LocalDate

@Composable
fun AddTransactionScreen(
    viewModel: TransactionViewModel = viewModel()
) {
    val valueFocusRequester = remember { FocusRequester() }
    var date by rememberSaveable { mutableStateOf<LocalDate?>(null) }
    var type by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("0.00") }
    var description by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // TODO - trazer as labels para fora dos compoenentes
        TransactionValueInput(label = "Valor", value = amount, onValueChange = { amount = it }, modifier = Modifier.focusRequester(valueFocusRequester))
        TransactionDropdown  (label = "Tipo", value = type, options = listOf("RECEITA", "DESPESA"), onValueChange = { type = it })//TODO passar pra ENUM ou banco
        DatePickerField(
            label = "Data do lançamento",
            date = date,
            onDateSelected = { date = it }
        )
     //   TransactionInputField(label = "Data do Lançamento:", value = date, onValueChange = { date = it })
        TransactionInputField(label = "Descrição:", value = description, onValueChange = { description = it })

        Spacer(modifier = Modifier.weight(1f))

            ExpenseActionButtons(
                onSave = {
                    viewModel.saveTransaction(
                        date = date,
                        type = type,
                        amount = amount,
                        description = description
                    )
                    valueFocusRequester.requestFocus()
                },
                onClear = {
                    date = null
                    type = ""
                    amount = "0.00"
                    description = ""
                    valueFocusRequester.requestFocus() //TODO ver pq nao funciona
                }
            )
        }
}

@Composable
fun ExpenseActionButtons(onSave: () -> Unit = {}, onClear: () -> Unit = {}) { //TODO trocar nome
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
        ExpenseActionButtons()
    }
}