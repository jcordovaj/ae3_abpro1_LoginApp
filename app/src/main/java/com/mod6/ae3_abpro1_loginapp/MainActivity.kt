package com.mod6.ae3_abpro1_loginapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mod6.ae3_abpro1_loginapp.presentation.LoginActivity

/**
 * Actividad Principal (Orquestador).
 * Inicia el LoginActivity inmediatamente, llevando al usuario a la pantalla de Login.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicia el login
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        // Finaliza la actividad para que el usuario no pueda volver a ella
        finish()
    }
}