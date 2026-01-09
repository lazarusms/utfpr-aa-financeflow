package com.utfpr.financeflow.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.utfpr.financeflow.model.TransactionType
import com.utfpr.financeflow.viewmodel.TransactionViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ListTransactionScreen(viewModel: TransactionViewModel) {
    val transactions   = viewModel.transactions
    val summaryIncome  = viewModel.totalIncome
    val summaryExpense = viewModel.totalExpenses
    val summaryBalance = viewModel.balance
    val monthLabel     = viewModel.formattedMonth

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Extrato",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { viewModel.previousMonth() }, modifier = Modifier.size(32.dp)) {
                            Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Anterior")
                        }
                        Text(text = monthLabel, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                        IconButton(onClick = { viewModel.nextMonth() }, modifier = Modifier.size(32.dp)) {
                            Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Próximo")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SummaryCard(
                        label = TransactionType.RECEITA.description,
                        value = viewModel.formatCurrency(summaryIncome),
                        containerColor = Color(0xFF2E7D32),
                        modifier = Modifier.weight(1f)
                    )
                    SummaryCard(
                        label = TransactionType.DESPESA.description,
                        value = viewModel.formatCurrency(summaryExpense),
                        containerColor = Color(0xFFC62828),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                val saldoColor = if (summaryBalance >= 0) Color(0xFF2E7D32) else Color(0xFFC62828)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("SALDO DO MÊS", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "R$ ${viewModel.formatCurrency(summaryBalance)}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = saldoColor
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "TRANSAÇÕES",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            if (transactions.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().padding(top = 32.dp), contentAlignment = Alignment.Center) {
                    Text("Nenhuma transação cadastrada.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                transactions.forEach { transaction ->
                    TransactionCardSimple(
                        description = transaction.description,
                        date = viewModel.formatDate(transaction.date),
                        amount = viewModel.formatCurrency(transaction.amount),
                        isIncome = transaction.type == TransactionType.RECEITA
                    )
                }
            }
        }
    }
}

@Composable
fun SummaryCard(
    label: String,
    value: String,
    containerColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = label, fontSize = 11.sp, color = Color.White)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "R$ $value",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun TransactionCardSimple(
    description: String,
    date: String,
    amount: String,
    isIncome: Boolean
) {
    val color = if (isIncome) Color(0xFF2E7D32) else Color(0xFFC62828)


    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, color),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(description, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                Text(date, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(
                text = "${if (isIncome) "+" else "-"} R$ $amount",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}