package com.utfpr.financeflow.repository

import com.utfpr.financeflow.database.DatabaseHandler
import com.utfpr.financeflow.entity.TransactionEntity
import com.utfpr.financeflow.model.Transaction
import com.utfpr.financeflow.model.TransactionType
import java.time.LocalDate

class TransactionRepository(private val dbHandler: DatabaseHandler) {

    fun getTransactions(): List<Transaction> {
        return dbHandler.listData().map { entity ->
            Transaction(
                description = entity.description,
                amount = entity.amount,
                date = LocalDate.parse(entity.date),
                type = TransactionType.valueOf(entity.transactionType)
            )
        }
    }

    fun addTransaction(transaction: Transaction) {
        val entity = TransactionEntity(
            id = 0, //sqlite vaia gerar o ID
            amount = transaction.amount,
            description = transaction.description,
            date = transaction.date.toString(),
            transactionType = transaction.type.name
        )
        dbHandler.insertData(entity)
    }
}