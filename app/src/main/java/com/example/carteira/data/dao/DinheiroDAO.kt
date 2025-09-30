package com.example.carteira.data.dao

import android.content.ContentValues
import android.content.Context
import com.example.carteira.model.Dinheiro
import com.example.carteira.data.db.DBHelper
import android.database.Cursor

class DinheiroDAO (private val context: Context) {

    private val dbHelper = DBHelper(context)

    fun getSaldo(): Double? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(DBHelper.TABLE_NAME_2, null, null, null, null, null, null)
        var saldo: Double? = null
        if (cursor.moveToFirst()) {
            saldo = cursor.getDouble(cursor.getColumnIndexOrThrow("saldo"))
        }
        cursor.close()
        db.close()
        return saldo
    }

    fun addSaldo(dinheiro: Dinheiro): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("saldo", dinheiro.saldo)
        }
        val id = db.insert(DBHelper.TABLE_NAME_2, null, values)
        db.close()
        return id
    }

    fun getDinheiroById(id: Int): Dinheiro? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            DBHelper.TABLE_NAME_2,
            null,
            "id = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var dinheiro: Dinheiro? = null
        if (cursor.moveToFirst()) {
            val saldo = cursor.getDouble(cursor.getColumnIndexOrThrow("saldo"))
            dinheiro = Dinheiro(id, saldo)
        }
        cursor.close()
        db.close()
        return dinheiro
    }

    fun updateSaldo(saldo: Double): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("saldo", saldo)
        }
        val linhasAfetadas = db.update(DBHelper.TABLE_NAME_2, values, null, null)
        db.close()

        return linhasAfetadas
    }

    fun close() {
        dbHelper.close()
    }
}