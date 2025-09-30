package com.example.carteira.controller

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.carteira.R
import com.example.carteira.data.dao.DinheiroDAO
import com.example.carteira.model.Transacao
import com.example.carteira.data.dao.TransacaoDAO

class Operacoes : AppCompatActivity() {
    //Deve permitir ao usuário cadastrar novas transações financeiras. O usuário deve marcar a
    //classificação da operação (débito ou crédito), informar uma descrição curta e o valor. Estes
    //registros devem ser gravados de maneira permanente, ou seja, toda vez que fecharmos o app,
    //não perderemos as transações anteriores. Guarde os registros em um BD.

    private lateinit var transacaoDAO: TransacaoDAO
    private lateinit var dinheiroDAO: DinheiroDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_operacoes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        transacaoDAO = TransacaoDAO(this)
        dinheiroDAO = DinheiroDAO(this)

        val spinnerType = findViewById<Spinner>(R.id.spinnerType)
        val buttonSave = findViewById<Button>(R.id.buttonSave)

        ArrayAdapter.createFromResource(
            this,
            R.array.type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerType.adapter = adapter
        }

        buttonSave.setOnClickListener {
            saveTransacao()
        }
    }
    private fun saveTransacao(){
        val spinnerType = findViewById<Spinner>(R.id.spinnerType)
        val editTextQtd = findViewById<EditText>(R.id.editTextQtd)
        val editTextMotive = findViewById<EditText>(R.id.editTextMotive)

        val type = spinnerType.selectedItem.toString()
        val qtd = editTextQtd.text.toString().toDoubleOrNull()
        val motive = editTextMotive.text.toString()

        if (motive.isBlank()) {
            editTextMotive.error = "Descrição não pode ser vazia."
            return
        }

        if (qtd == null || qtd <= 0) {
            editTextQtd.error = "Por favor, insira um valor válido."
            return
        }

        if (qtd == null || qtd <= 0) {
            editTextQtd.error = "Por favor, insira um valor válido."
            return
        }

        val saldo = dinheiroDAO.getSaldo() ?: 0.0

        val novoSaldo = if (type == "Crédito") saldo + qtd else saldo - qtd

        val transacao = Transacao(
            id = -1,
            qtd = qtd,
            motive = motive,
            type = if (type == "Crédito") "Credito" else "Debito"
        )

        val sucesso = transacaoDAO.addTransacao(transacao)

        if (sucesso != -1L) {
            val linhasAfetadas = dinheiroDAO.updateSaldo(novoSaldo)
            Toast.makeText(this, "Transação salva com sucesso!", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Erro ao salvar a transação.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        transacaoDAO.close()
        dinheiroDAO.close()
    }
}