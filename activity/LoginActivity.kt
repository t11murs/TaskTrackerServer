package com.example.habittracker.ui.activity


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.habittracker.ui.screen.LoginScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.instance.RetrofitInstance
import model.request.LoginRequest

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                onLoginClicked = { email, password -> login(email, password) },
                onRegisterClicked = {
                    startActivity(Intent(this, RegisterActivity::class.java))
                    finish()
                }
            )
        }
    }

    private fun login(email: String, password: String) {
        val sharedPref = getSharedPreferences("auth", MODE_PRIVATE)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val loginRequest = LoginRequest(email, password)
                val response = RetrofitInstance.api.login(loginRequest)
                if (response.isSuccessful) {
                    val token = response.body()?.string().orEmpty()
                    sharedPref.edit().putString("jwt", token).apply()
                    withContext(Dispatchers.Main) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, "Ошибка входа: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginActivity, "Ошибка сети", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
