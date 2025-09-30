package com.example.carteira.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.carteira.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnCadastro = findViewById<Button>(R.id.btn_cadastro)
        val btnExtrato = findViewById<Button>(R.id.btn_extrato)
        val btnSair = findViewById<Button>(R.id.btn_sair)

        btnCadastro.setOnClickListener {
            val intent = Intent(this, Operacoes::class.java)
            startActivity(intent)
        }

        btnExtrato.setOnClickListener {
            val intent = Intent(this, Extrato::class.java)
            startActivity(intent)
        }

        btnSair.setOnClickListener {
            finish()
        }
    }
}