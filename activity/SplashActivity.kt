package com.example.habittracker.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val token = getSharedPreferences("auth", MODE_PRIVATE)
            .getString("jwt", null)

        val next = if (!token.isNullOrEmpty()) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }

        // 3. Стартуем и завершаем SplashActivity
        startActivity(next)
        finish()
    }
}