package com.example.habittracker.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.habittracker.ui.screen.MainContent

class MainActivity : ComponentActivity() {

    // Когда мы возвращаемся сюда (onResume), мы будем инкрементировать этот счетчик.
    // Compose “читает” reloadTrigger.value, поэтому при изменении Compose пересобирает MainContent.
    private var reloadTrigger by mutableStateOf(0)

    override fun onResume() {
        super.onResume()
        // Любой возврат (включая возвращение из NewTaskActivity или NewHabitActivity)
        // увеличивает reloadTrigger, и Compose увидит, что он “пропал” и пересоберёт MainContent.
        reloadTrigger++
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jwt = getSharedPreferences("auth", MODE_PRIVATE)
            .getString("jwt", null)

        if (jwt.isNullOrEmpty()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContent {
            // Передаём jwt и текущее значение reloadTrigger.
            // Compose будет следить за reloadTrigger.value и пересоберёт MainContent, когда мы его изменим.
            MainContent(jwt, reloadTrigger)
        }
    }
}
