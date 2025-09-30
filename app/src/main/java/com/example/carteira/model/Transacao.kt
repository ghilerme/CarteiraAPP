package com.example.carteira.model

data class Transacao (
    val id: Int = 0,
    val qtd: Double,
    val motive: String,
    val type: String
){
    override fun toString(): String {
        return "Transacao(id=$id, qtd=$qtd, motive='$motive', type='$type')"
    }
}