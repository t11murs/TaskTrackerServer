package com.example.habittracker.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habittracker.ui.screen.TaskScreen
import com.example.habittracker.ui.viewmodel.TaskViewModel

class TaskActivity : ComponentActivity() {

    private val vm: TaskViewModel by viewModels()

    // Лаунчер для NewTaskActivity
    private val newTaskLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Если NewTaskActivity вернулась с RESULT_OK, перезагружаем список
            vm.loadTasks()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Получаем JWT из SharedPreferences и передаём во ViewModel
        vm.jwt = getSharedPreferences("auth", MODE_PRIVATE)
            .getString("jwt", "") ?: ""

        setContent {
            val isLoading by vm.isLoading.collectAsState(initial = true)
            val tasks     by vm.tasks.collectAsState(initial = emptyList())
            val context   = this@TaskActivity

            // Подписываемся на поток ошибок и показываем Toast
            LaunchedEffect(Unit) {
                vm.error.collect { msg ->
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }
            }

            if (isLoading) {
                // Пока идёт загрузка, показываем индикатор
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            } else {
                // Когда загрузка завершена, рендерим экран задач
                TaskScreen(
                    tasks = tasks,
                    onAddClick = {
                        // Запускаем NewTaskActivity для создания новой задачи
                        val intent = Intent(this@TaskActivity, NewTaskActivity::class.java)
                        newTaskLauncher.launch(intent)
                    }
                )
            }
        }

        // Сразу после создания активности загружаем существующие задачи
        vm.loadTasks()
    }
}
