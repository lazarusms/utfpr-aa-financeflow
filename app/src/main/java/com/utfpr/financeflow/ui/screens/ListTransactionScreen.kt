package com.utfpr.financeflow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.utfpr.financeflow.model.TransactionType
import com.utfpr.financeflow.ui.components.TransactionCardSimple
import com.utfpr.financeflow.ui.components.TransactionSummaryCard
import com.utfpr.financeflow.viewmodel.TransactionViewModel

@Composable
fun ListTransactionScreen(viewModel: TransactionViewModel) {
    val transactions = viewModel.transactions
    val summaryIncome = viewModel.totalIncome
    val summaryExpense = viewModel.totalExpenses
    val summaryBalance = viewModel.balance
    val monthLabel = viewModel.formattedMonth

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        item {
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
                        TransactionSummaryCard(
                            label = TransactionType.RECEITA.description,
                            value = viewModel.formatCurrency(summaryIncome),
                            containerColor = Color(0xFF2E7D32),
                            modifier = Modifier.weight(1f)
                        )
                        TransactionSummaryCard(
                            label = TransactionType.DESPESA.description,
                            value = viewModel.formatCurrency(summaryExpense),
                            containerColor = Color(0xFFC62828),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    val balanceColor = if (summaryBalance >= 0) Color(0xFF2E7D32) else Color(0xFFC62828)
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
                                color = balanceColor
                            )
                        }
                    }
                }
            }
        }
        item {
            Text(
                text = "TRANSAÇÕES",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
            )
        }
        if (transactions.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Nenhuma transação cadastrada.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(transactions.size) { index ->
                val transaction = transactions[index]
                Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)) {
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