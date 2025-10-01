package com.example.carteira.controller

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carteira.R
import com.example.carteira.adapter.ExtratoAdapter
import com.example.carteira.data.dao.DinheiroDAO
import com.example.carteira.data.dao.TransacaoDAO
import com.example.carteira.databinding.ActivityExtratoBinding
import com.example.carteira.model.Dinheiro
import java.text.NumberFormat
import java.util.Locale

class Extrato : AppCompatActivity() {

    private lateinit var binding: ActivityExtratoBinding
    private lateinit var transacaoDAO: TransacaoDAO
    private lateinit var dinheiroDAO: DinheiroDAO
    private val extratoAdapter = ExtratoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExtratoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transacaoDAO = TransacaoDAO(this)
        dinheiroDAO = DinheiroDAO(this)

        setupRecyclerView()
        setupBotaoVoltar()
        atualizarSaldo()
        carregarTransacoes()
        setupFiltros()
    }

    private fun setupBotaoVoltar() {
        binding.btnVoltar.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        binding.rvTransacoes.apply {
            layoutManager = LinearLayoutManager(this@Extrato)
            adapter = extratoAdapter
        }
    }

    private fun carregarTransacoes() {
        val lista = transacaoDAO.getAllChars()
        extratoAdapter.setTransacoes(lista)
    }

    private fun atualizarSaldo() {
        //val dinheiro = dinheiroDAO.getDinheiroById(0)
        if (dinheiroDAO.getSaldo() == null) {
            dinheiroDAO.addSaldo(Dinheiro(saldo = 0.0))
        }
        val saldo = dinheiroDAO.getSaldo()
        Log.d("Ronaldo", "ronaldo $saldo")
        val formatadorMoeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        binding.tvSaldo.text = formatadorMoeda.format(saldo)
    }

    private fun setupFiltros() {
        binding.rgFiltros.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbTodos -> carregarTransacoes(null) // null para "Todos"
                R.id.rbCreditos -> carregarTransacoes("Credito")
                R.id.rbDebitos -> carregarTransacoes("Debito")
            }
        }
    }

    private fun carregarTransacoes(filtro: String?) {
        if (filtro == "Credito") {
            val lista = transacaoDAO.getAllChars().filter { it.type == "Credito" }
            extratoAdapter.setTransacoes(lista)
            return
        } else if (filtro == "Debito") {
            val lista = transacaoDAO.getAllChars().filter { it.type == "Debito" }
            extratoAdapter.setTransacoes(lista)
            return
        }

        val lista = transacaoDAO.getAllChars()
        extratoAdapter.setTransacoes(lista)
    }
}