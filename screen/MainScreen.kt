// com/example/habittracker/ui/screen/MainContent.kt

package com.example.habittracker.ui.screen

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.habittracker.ui.activity.NewTaskActivity
import model.instance.RetrofitInstance
import model.response.TaskResponse
import model.response.UserResponse
import retrofit2.HttpException
import java.io.IOException

/**
 * Основной Compose-экран с BottomNavigation: Задачи / Профиль.
 * Параметр reloadTrigger следит за тем, чтобы при возвращении из NewTaskActivity
 * заново подтягивать список задач.
 */
@Composable
fun MainContent(jwt: String, reloadTrigger: Int) {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf(0) }

    var tasks by remember { mutableStateOf<List<TaskResponse>>(emptyList()) }
    var user by remember { mutableStateOf<UserResponse?>(null) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    // При изменении jwt или reloadTrigger заново запрашиваем задачи
    LaunchedEffect(jwt, reloadTrigger) {
        try {
            RetrofitInstance.api.getTasks("Bearer $jwt").let { resp ->
                if (resp.isSuccessful) {
                    tasks = resp.body() ?: emptyList()
                } else {
                    errorMsg = "Ошибка загрузки задач: ${resp.code()}"
                }
            }
        } catch (e: IOException) {
            errorMsg = "Сетевая ошибка"
        } catch (e: HttpException) {
            errorMsg = "Ошибка сервера"
        }
    }

    // При переключении на таб «Профиль» тянем user-данные
    LaunchedEffect(selectedTab) {
        if (selectedTab == 1) {
            try {
                val resp = RetrofitInstance.api.getMe("Bearer $jwt")
                if (resp.isSuccessful) {
                    user = resp.body()
                } else {
                    errorMsg = "Ошибка профиля: ${resp.code()}"
                }
            } catch (e: Exception) {
                errorMsg = "Сетевая ошибка при профиле"
            }
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Edit, contentDescription = "Задачи") },
                    label = { Text("Задачи") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Профиль") },
                    label = { Text("Профиль") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
            }
        }
    ) { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            errorMsg?.let { msg ->
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                errorMsg = null
            }

            when (selectedTab) {
                0 -> {
                    TaskScreen(
                        tasks = tasks,
                        onAddClick = {
                            context.startActivity(Intent(context, NewTaskActivity::class.java))
                        }
                    )
                }
                1 -> {
                    user?.let { ProfileScreen(it) }
                        ?: Text(
                            text = "Загрузка профиля...",
                            style = MaterialTheme.typography.bodyMedium
                        )
                }
            }
        }
    }
}
