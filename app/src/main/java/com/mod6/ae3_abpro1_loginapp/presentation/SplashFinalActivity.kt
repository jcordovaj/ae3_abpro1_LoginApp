package com.mod6.ae3_abpro1_loginapp.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mod6.ae3_abpro1_loginapp.R

class SplashFinalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val username = intent.getStringExtra("USERNAME_KEY") ?: "Usuario"
        val token    = intent.getStringExtra("TOKEN_KEY") ?: "N/A"

        val tvWelcome: TextView = findViewById(R.id.textViewWelcome)
        val btnContinue: Button = findViewById(R.id.buttonContinue)

        tvWelcome.text = "Bienvenido $username\nToken JWT: $token"

        btnContinue.setOnClickListener {
            // Acción por defecto: salir (o navegar a la app activa)
            finish()
        }
    }
}