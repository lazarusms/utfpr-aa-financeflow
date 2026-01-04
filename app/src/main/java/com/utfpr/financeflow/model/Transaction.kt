package com.utfpr.financeflow.model

import java.time.LocalDate
data class Transaction(
    val description: String,
    val date: LocalDate,
    val amount: Double,
    val type: TransactionType
)