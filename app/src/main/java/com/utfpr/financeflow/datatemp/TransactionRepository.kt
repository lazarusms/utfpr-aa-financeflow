package com.utfpr.financeflow.datatemp

import androidx.compose.runtime.mutableStateListOf
import java.time.LocalDate

// repo temporarário pra realizar testes das telas
class TransactionRepository {
    private val _transactions = mutableStateListOf<TransactionTemp>(
        TransactionTemp("Salário", LocalDate.of(2026, 1, 1), 5000.0, TransactionType.RECEITA),
        TransactionTemp("Aluguel", LocalDate.of(2026, 1, 3), 1200.0, TransactionType.DESPESA),
        TransactionTemp("Aluguel2", LocalDate.of(2025, 1, 3), 1200.0, TransactionType.DESPESA),
        TransactionTemp("Mercado", LocalDate.of(2025, 12, 15), 1200.0, TransactionType.DESPESA)
    )
    fun getTransactions(): List<TransactionTemp> = _transactions

    fun addTransaction(item: TransactionTemp) {
        _transactions.add(item)
    }
}
