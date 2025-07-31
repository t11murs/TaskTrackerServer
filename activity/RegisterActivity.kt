package com.example.habittracker.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.habittracker.ui.screen.RegisterScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.instance.RetrofitInstance
import model.request.RegisterRequest

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RegisterScreen(
                onRegisterClicked = { name, email, password ->
                    register(name, email, password)
                }
            )
        }
    }

    private fun register(name: String, email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = RegisterRequest(name, email, password)
                val response = RetrofitInstance.api.register(request)

                val user = response.body()
                if (user != null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterActivity, "Регистрация успешна!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterActivity, "Ошибка регистрации", Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RegisterActivity, "Ошибка сети: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
