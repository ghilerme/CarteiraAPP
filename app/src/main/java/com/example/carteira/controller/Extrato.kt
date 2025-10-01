package com.example.carteira.controller

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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
        atualizarSaldo()
        carregarTransacoes()
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
}