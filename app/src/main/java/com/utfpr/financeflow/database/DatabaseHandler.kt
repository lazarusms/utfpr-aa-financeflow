package com.utfpr.financeflow.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.utfpr.financeflow.entity.TransactionEntity


class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COL_ID PRIMARY KEY AUTOINCREMENT, $COL_AMOUNT REAL, $COL_DESC TEXT, $COL_DATE TEXT, $COL_TRANSACTION_TYPE)")

    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)

    }


    companion object {
        private const val DATABASE_NAME = "financeflow.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "TransactionEntity"
        private const val COL_ID = "ID"
        private const val COL_AMOUNT = "AMOUNT"
        private const val COL_DESC = "DESCRIPTION"
        private const val COL_DATE = "DATE"
        private const val COL_TRANSACTION_TYPE = "TRANSACTION_TYPE"
    }

    fun insertData(te: TransactionEntity) {
        val db = this.writableDatabase
        val registro = ContentValues()

        registro.put(COL_AMOUNT, te.amount)
        registro.put(COL_DESC, te.description)
        registro.put(COL_DATE, te.date)
        registro.put(COL_TRANSACTION_TYPE, te.transactionType)

        db.insert(TABLE_NAME, null, registro)

    }

    fun listData(): MutableList<TransactionEntity> {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        val listTransactionEntity = mutableListOf<TransactionEntity>()

        while (cursor.moveToNext()) {
            val te = TransactionEntity(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_AMOUNT)),
                description = cursor.getString(cursor.getColumnIndexOrThrow(COL_DESC)),
                date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE)),
                transactionType = cursor.getString(cursor.getColumnIndexOrThrow(COL_TRANSACTION_TYPE))
            )
            listTransactionEntity.add(te)
        }

        cursor.close()
        return listTransactionEntity

    }


}