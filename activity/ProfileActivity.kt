package com.example.habittracker.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.instance.RetrofitInstance
import model.response.UserResponse

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("auth", MODE_PRIVATE)
        val token = sharedPref.getString("jwt", null)
        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Токен не найден", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setContent {
            val userState = remember { mutableStateOf<UserResponse?>(null) }

            LaunchedEffect(Unit) {
                try {
                    val resp = RetrofitInstance.api.getMe("Bearer $token")
                    if (resp.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            userState.value = resp.body()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@ProfileActivity,
                                "Ошибка ${resp.code()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ProfileActivity, "Ошибка сети", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}