package com.utfpr.financeflow.datatemp

import java.time.LocalDate

// TODO - passar para classe separada o type?
enum class TransactionType {
    RECEITA,
    DESPESA
}

//classe temporaria pra mockar dados de transação
// alterar para a correta
// ter uma pasta model e repository ?
data class TransactionTemp(
    val description: String,
    val date: LocalDate,
    val amount: Double,
    val type: TransactionType
)