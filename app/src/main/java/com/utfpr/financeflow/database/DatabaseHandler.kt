package com.utfpr.financeflow.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.utfpr.financeflow.entity.Cadastro


class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COL_ID PRIMARY KEY AUTOINCREMENT, $COL_VALOR REAL, $COL_DESC TEXT, $COL_DT_LANCAMENTO TEXT, $COL_TIPO)")

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
        private const val TABLE_NAME = "cadastro"

        private const val COL_ID = "ID_LANCAMENTO"
        private const val COL_VALOR = "VALOR"
        private const val COL_DESC = "DESCRICAO"
        private const val COL_DT_LANCAMENTO = "DT_LANCAMENTO"
        private const val COL_TIPO = "TIPO_LANCAMENTO"
    }

    fun insertData(cadastro: Cadastro) {
        val db = this.writableDatabase
        val registro = ContentValues()

        registro.put(COL_VALOR, cadastro.valor)
        registro.put(COL_DESC, cadastro.descricao)
        registro.put(COL_DT_LANCAMENTO, cadastro.dt_lancamento)
        registro.put(COL_TIPO, cadastro.tipo_lancamento)

        db.insert(TABLE_NAME, null, registro)

    }

    fun listData() {

    }


}