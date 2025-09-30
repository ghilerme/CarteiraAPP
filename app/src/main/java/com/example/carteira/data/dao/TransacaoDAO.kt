package com.example.carteira.data.dao

import android.content.ContentValues
import android.content.Context
import com.example.carteira.model.Transacao
import com.example.carteira.data.db.DBHelper
import android.database.Cursor

class TransacaoDAO (private val context: Context) {
    private val dbHelper = DBHelper(context)

    fun addTransacao(transacao: Transacao): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("qtd", transacao.qtd)
            put("motive", transacao.motive)
            put("type", transacao.type)
        }
        val id = db.insert(DBHelper.TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun getAllChars(): List<Transacao> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null)
        val transacaoList = mutableListOf<Transacao>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val qtd = cursor.getDouble(cursor.getColumnIndexOrThrow("qtd"))
            val motive = cursor.getString(cursor.getColumnIndexOrThrow("motive"))
            val type = cursor.getString(cursor.getColumnIndexOrThrow("type"))
            transacaoList.add(Transacao(id, qtd, motive, type))
        }
        cursor.close()
        db.close()
        return transacaoList
    }

    fun getTransacaoById(id: Int): Transacao? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(DBHelper.TABLE_NAME, null, "id = ?", arrayOf(id.toString()), null, null, null)

        var transacao: Transacao? = null
        if (cursor.moveToFirst()){
            val qtd = cursor.getDouble(cursor.getColumnIndexOrThrow("qtd"))
            val motive = cursor.getString(cursor.getColumnIndexOrThrow("motive"))
            val type = cursor.getString(cursor.getColumnIndexOrThrow("type"))
            transacao = Transacao(id, qtd, motive, type)
        }
        cursor.close()
        db.close()
        return transacao
    }

    fun close() {
        dbHelper.close()
    }
}