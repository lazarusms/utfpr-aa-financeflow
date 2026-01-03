package com.utfpr.financeflow.entity

class TransactionEntity(
    var id: Int,
    var amount: Double,
    var description: String,
    var date: String,
    var transactionType: String
) {
}