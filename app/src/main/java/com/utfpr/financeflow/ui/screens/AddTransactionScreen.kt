package com.utfpr.financeflow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.utfpr.financeflow.model.TransactionViewModel
import com.utfpr.financeflow.ui.components.TransactionDropdown
import com.utfpr.financeflow.ui.components.TransactionInputField
import com.utfpr.financeflow.ui.theme.FinanceFlowTheme

@Composable
fun AddTransactionScreen(
    viewModel: TransactionViewModel = viewModel()
) {
    var date by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Novo Lançamento", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(24.dp))

        TransactionInputField(label = "Data do Lançamento:", value = date, onValueChange = { date = it })
       // ExpenseInputField(label = "Tipo:", value = category, onValueChange = { category = it })
        //TODO - trocar pra radio por conta da doc.?
        TransactionDropdown(label = "Tipo", value = category, options = listOf("RECEITA", "DESPESA"), onValueChange = { category = it })//TODO passar pra ENUM ou banco
        TransactionInputField(label = "Valor:", value = amount, onValueChange = { amount = it })
        TransactionInputField(label = "Descrição:", value = description, onValueChange = { description = it })

        Spacer(modifier = Modifier.weight(1f))

        ExpenseActionButtons(
            onSave = {
                viewModel.saveTransaction(
                    date = date,
                    category = category,
                    amount = amount,
                    description = description
                )
            },
            onClear = {
                date = ""
                category = ""
                amount = ""
                description = ""
            }//TODO voltar pro primeiro campo no clear e salvar estado qnd volta de extrato
        )
    }
}

@Composable
fun ExpenseActionButtons(onSave: () -> Unit = {}, onClear: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth(),// deixar exposto?
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