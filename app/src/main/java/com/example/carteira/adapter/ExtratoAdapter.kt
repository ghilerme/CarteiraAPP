package com.example.carteira.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carteira.model.Transacao
import java.util.Locale
import java.text.NumberFormat

class ExtratoAdapter : RecyclerView.Adapter<ExtratoAdapter.ExtratoViewHolder>() {
    private var transacoes = listOf<Transacao>()

    fun setTransacoes(lista: List<Transacao>) {
        this.transacoes = lista
        notifyDataSetChanged() // notify the RecyclerView that the data has changed
    }

    fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): ExtratoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_extrato, parent, false)
        return TransacaoViewHolder(view)
    }

    override fun getItemCount() = transacoes.size

    override fun onBindViewHolder(holder: TransacaoViewHolder, position: Int) {
        val transacao = transacoes[position]
        holder.bind(transacao)
    }

    class TransacaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDescricao: TextView = itemView.findViewById(R.id.tvDescricao)
        private val tvValor: TextView = itemView.findViewById(R.id.tvValor)

        fun bind(transacao: Transacao) {
            val formatadorMoeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
            val valorFormatado = formatadorMoeda.formtar(transacao.qtd)

            if (transacao.type == "Credito") {
                tvValor.text = "+ $valorFormatado"
            } else {
                tvValor.text = "- $valorFormatado"
            }
        }
    }
}