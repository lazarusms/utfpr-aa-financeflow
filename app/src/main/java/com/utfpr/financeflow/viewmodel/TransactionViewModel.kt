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
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    //dependencias
    private val dbHandler = DatabaseHandler(application)
    private val repository = TransactionRepository(dbHandler)

    //estados da UI
    var amount by mutableStateOf("0,00")

    var type by mutableStateOf("")
    var date by mutableStateOf<LocalDate?>(null)
    var description by mutableStateOf("")
    var selectedMonth: YearMonth by mutableStateOf(YearMonth.now())
        private set
    var transactions by mutableStateOf<List<Transaction>>(emptyList())
        private set

    var message by mutableStateOf<String?>(null)
        private set

    var isError by mutableStateOf(false)
        private set

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

    fun formatDate(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }

    fun updateAmount(newValue: String) {
        val digits = newValue.filter { it.isDigit() }

        if (digits.isEmpty()) {
            amount = ""
            return
        }

        val cents = digits.toLongOrNull() ?: 0L
        val value = cents / 100.0

        if (cents > 100_000_000) return

        val formatter = DecimalFormat("#,##0.00", DecimalFormatSymbols(Locale("pt", "BR")))
        amount = formatter.format(value)
    }

    fun formatCurrency(value: Double): String {
        val formatter = DecimalFormat("#,##0.00", DecimalFormatSymbols(Locale("pt", "BR")))
        return formatter.format(value)
    }

    // não validar se a data é anterior ou superior ao mês atual é uma escolha
    // para dar sentido ao uso de incluir transações no futuro (como um salário que irá cair)
    // ou no passado (que foram esquecidas, por exemplo).

    fun saveTransaction() {
        val sanitizedAmount = amount
            .replace(".", "")
            .replace(",", ".")

        val currentAmount = sanitizedAmount.toDoubleOrNull() ?: 0.0
        val currentDate = date

        val validationError = when {
            currentAmount <= 0.0 -> "Valor deve ser maior que zero"
            type.isBlank() -> "Selecione o tipo da transação"
            currentDate == null -> "Selecione uma data"
            description.isBlank() -> "Preencha a descrição"
            else -> null
        }

        if (validationError != null) {
            message = validationError
            isError = true
            return
        }

        val newTransaction = Transaction(
            description = description,
            date = currentDate!!,
            amount = currentAmount,
            type = TransactionType.valueOf(type)
        )

        repository.addTransaction(newTransaction)
        refreshTransactions()
        clearFields()
        message = "Transação salva com sucesso!"
        isError = false
    }



    fun refreshTransactions() {
        transactions =
            repository.getTransactions()
            .filter { YearMonth.from(it.date) == selectedMonth }
            .sortedByDescending { it.date }
    }

    fun clearFields() {
        amount = "0,00"
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

    fun clearMessage() {
        message = null
    }
}
