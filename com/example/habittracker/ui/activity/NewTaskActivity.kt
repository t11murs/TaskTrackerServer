package com.example.habittracker.ui.activity

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.activity.viewModels
import com.example.habittracker.ui.viewmodel.TaskViewModel
import kotlinx.coroutines.flow.SharedFlow
import model.request.TaskRequest
import java.time.LocalDate
import java.time.LocalTime

/**
 * Компонент, отвечающий за отображение экрана создания новой задачи без логики напоминаний.
 */
@Composable
private fun NewTaskContent(
    onSubmit: (TaskRequest) -> Unit,
    isLoading: Boolean,
    errorFlow: SharedFlow<String>
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var deadlineDate by remember { mutableStateOf<LocalDate?>(null) }
    var deadlineTime by remember { mutableStateOf<LocalTime?>(null) }

    val dateFormatter = java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val timeFormatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm")
    val context = LocalContext.current

    // DatePickerDialog
    val today = java.util.Calendar.getInstance()
    val datePicker = remember {
        android.app.DatePickerDialog(
            context,
            { _, y, m, d -> deadlineDate = LocalDate.of(y, m + 1, d) },
            today.get(java.util.Calendar.YEAR),
            today.get(java.util.Calendar.MONTH),
            today.get(java.util.Calendar.DAY_OF_MONTH)
        )
    }
    // TimePickerDialog
    val now = java.util.Calendar.getInstance()
    val timePicker = remember {
        android.app.TimePickerDialog(
            context,
            { _, h, min -> deadlineTime = LocalTime.of(h, min) },
            now.get(java.util.Calendar.HOUR_OF_DAY),
            now.get(java.util.Calendar.MINUTE),
            true
        )
    }

    // Подписка на ошибки из ViewModel
    LaunchedEffect(Unit) {
        errorFlow.collect { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Новая задача", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Название") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Описание (необязательно)") },
            modifier = Modifier.fillMaxWidth()
        )

        // Дата дедлайна
        OutlinedTextField(
            value = deadlineDate?.format(dateFormatter) ?: "",
            onValueChange = { },
            readOnly = true,
            label = { Text("Дата дедлайна") },
            trailingIcon = {
                IconButton(onClick = { datePicker.show() }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Выбрать дату")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Время дедлайна
        OutlinedTextField(
            value = deadlineTime?.format(timeFormatter) ?: "",
            onValueChange = { },
            readOnly = true,
            label = { Text("Время дедлайна") },
            trailingIcon = {
                IconButton(onClick = { timePicker.show() }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Выбрать время")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (name.isBlank()) {
                    Toast.makeText(context, "Введите название задачи", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                // Создаём запрос без полей remind/remindTime (они остаются null/false по умолчанию).
                val request = TaskRequest(
                    name = name,
                    description = description.ifBlank { null },
                    deadlineDate = deadlineDate,
                    deadlineTime = deadlineTime,
                    remind = false,
                    remindTime = null
                )
                onSubmit(request)
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
            } else {
                Text("Создать")
            }
        }
    }
}

/**
 * Activity для создания новой задачи.
 * Здесь убрана вся логика работы с напоминаниями.
 */
class NewTaskActivity : ComponentActivity() {

    private val vm: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Читаем JWT из SharedPreferences и передаём во ViewModel
        vm.jwt = getSharedPreferences("auth", MODE_PRIVATE)
            .getString("jwt", "") ?: ""

        setContent {
            val isLoading by vm.isLoading.collectAsState(initial = false)

            NewTaskContent(
                onSubmit = { request ->
                    vm.createTask(request) {
                        // После успешного создания возвращаем RESULT_OK и закрываем активити
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                },
                isLoading = isLoading,
                errorFlow = vm.error
            )
        }
    }
}
