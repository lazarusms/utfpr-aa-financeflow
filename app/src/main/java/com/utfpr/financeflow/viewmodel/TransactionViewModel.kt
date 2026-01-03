package com.utfpr.financeflow.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.utfpr.financeflow.datatemp.TransactionRepository
import com.utfpr.financeflow.datatemp.TransactionTemp
import com.utfpr.financeflow.datatemp.TransactionType
import java.time.LocalDate
import java.time.YearMonth
import kotlin.collections.sumOf

class TransactionViewModel : ViewModel() {
    private val repository = TransactionRepository()

    var selectedMonth by mutableStateOf(YearMonth.now())
        private set

    val transactions: List<TransactionTemp>
        get() = repository.getTransactions().filter {
            YearMonth.from(it.date) == selectedMonth
        }

    fun previousMonth() { selectedMonth = selectedMonth.minusMonths(1) }
    fun nextMonth() { selectedMonth = selectedMonth.plusMonths(1) }

    val totalExpenses
        get() = transactions.filter { it.type == TransactionType.DESPESA }.sumOf { it.amount }

    val totalIncome
        get() = transactions.filter { it.type == TransactionType.RECEITA }.sumOf { it.amount }

    val balance
        get() = totalIncome - totalExpenses

    fun saveTransaction(date: LocalDate?, type: String, amount: String, description: String) {
        if (date == null) return
        // TODO - validacoes de dados / retornar msg de erro ?
        val newTransaction = TransactionTemp(
            description = description,
            date = date,
            amount = amount.toDoubleOrNull() ?: 0.0,
            type = TransactionType.valueOf(type)
        )

        repository.addTransaction(newTransaction)
    }
}