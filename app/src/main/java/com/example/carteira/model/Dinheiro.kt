package com.example.carteira.model

data class Dinheiro (
    val id: Int = 0,
    val saldo: Double
) {
    override fun toString(): String {
        return "Dinheiro(id=$id, saldo=$saldo)"
    }
}
