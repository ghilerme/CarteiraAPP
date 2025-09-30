package com.example.carteira.data.db

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase


class DBHelper(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "walletDB.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "transacoes"
        const val TABLE_NAME_2 = "dinheiro"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                qtd DOUBLE,
                motive TEXT,
                type TEXT CHECK(type IN ('Credito', 'Debito')) NOT NULL 
                DEFAULT 'Debito'
            )
        """.trimIndent()

        val createTable2 = """
            CREATE TABLE $TABLE_NAME_2 (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                saldo DOUBLE
                )
        """.trimIndent()

        db.execSQL(createTable2)
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_2")
        onCreate(db)
    }
}