package com.utfpr.financeflow.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.utfpr.financeflow.database.DatabaseHandler
import com.utfpr.financeflow.model.Transaction
import com.utfpr.financeflow.model.TransactionType
import com.utfpr.financeflow.repository.TransactionRepository
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

// TODO - verificar se ter acesso ao getapplcation aqui é a melhor forma 'getApplication()'
class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    //dependencias
    private val dbHandler = DatabaseHandler(application)
    private val repository = TransactionRepository(dbHandler)

    //estados da UI
    var amount by mutableStateOf("0.00")
    var type by mutableStateOf("")
    var date by mutableStateOf<LocalDate?>(null)
    var description by mutableStateOf("")
    var selectedMonth: YearMonth by mutableStateOf(YearMonth.now())
        private set
    var transactions by mutableStateOf<List<Transaction>>(emptyList())
        private set

    // precisa pq nao estamos usando room
    init {
        refreshTransactions()
    }

    val totalExpenses get() = transactions.filter { it.type == TransactionType.DESPESA }.sumOf { it.amount }
    val totalIncome get() = transactions.filter { it.type == TransactionType.RECEITA }.sumOf { it.amount }
    val balance get() = totalIncome - totalExpenses

    val formattedMonth
        get () = selectedMonth.format(
            DateTimeFormatter.ofPattern(
                "MMMM yyyy",
                Locale("pt", "BR")
            )
        ).replaceFirstChar { it.uppercase() }

    fun saveTransaction() {
        val currentAmount = amount.toDoubleOrNull() ?: 0.0
        val currentDate = date

        if (description.isBlank() || currentAmount <= 0.0 || currentDate == null || type.isBlank()) {
            return //TODO - criar validações / dá pra retornar mensagem do que está faltando e exibir num dialog
        }

        val newTransaction = Transaction(
            description = description,
            date = currentDate,
            amount = currentAmount,
            type = TransactionType.valueOf(type)
        )

        repository.addTransaction(newTransaction)
        refreshTransactions()
        clearFields()
    }

    fun refreshTransactions() {
        transactions = repository.getTransactions().filter {
            YearMonth.from(it.date) == selectedMonth
        }
    }

    fun clearFields() {
        amount = "0.00"
        type = ""
        date = null
        description = ""
    }

    fun previousMonth() {
        selectedMonth = selectedMonth.minusMonths(1)
        refreshTransactions()
    }

    fun nextMonth() {
        selectedMonth = selectedMonth.plusMonths(1)
        refreshTransactions()
    }
}
