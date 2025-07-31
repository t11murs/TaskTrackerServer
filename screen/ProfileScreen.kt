// com/example/habittracker/ui/screen/ProfileScreen.kt

package com.example.habittracker.ui.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import model.response.UserResponse
import com.example.habittracker.ui.activity.LoginActivity

@Composable
fun ProfileScreen(user: UserResponse) {
    // Получаем контекст и приводим к Activity, чтобы можно было вызвать finish()
    val context = LocalContext.current
    val activity = (context as? Activity)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Имя: ${user.name}",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Email: ${user.email}",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Просто список задач без заголовка
        if (!user.tasks.isNullOrEmpty()) {
            (user.tasks ?: emptyList()).forEach { task ->
                Text(
                    text = "- ${task.name}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                context.getSharedPreferences("auth", Context.MODE_PRIVATE)
                    .edit()
                    .remove("jwt")
                    .apply()

                val intent = Intent(context, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.startActivity(intent)

                activity?.finish()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Выйти из аккаунта")
        }
    }
}
