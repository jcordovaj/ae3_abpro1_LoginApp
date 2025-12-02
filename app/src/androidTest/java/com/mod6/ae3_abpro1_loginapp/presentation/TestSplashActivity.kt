package com.mod6.ae3_abpro1_loginapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Splash mínimo para pruebas de UI.
 * Solo lanza LoginActivity inmediatamente.
 */
class TestSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, LoginActivity::class.java))
        finish() // cerrar splash
    }
}