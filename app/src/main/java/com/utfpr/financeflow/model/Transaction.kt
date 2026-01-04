package com.utfpr.financeflow.model

import java.time.LocalDate

//classe temporaria pra mockar dados de transação
// alterar para a correta
// ter uma pasta model e repository ?
data class Transaction(
    val description: String,
    val date: LocalDate,
    val amount: Double,
    val type: TransactionType
)