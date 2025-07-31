package com.example.habittracker.ui.activity

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.instance.RetrofitInstance
import model.request.TaskRequest
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class EditTaskActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val taskId         = intent.getLongExtra("taskId", -1L)
        val taskName       = intent.getStringExtra("taskName") ?: ""
        val taskDesc       = intent.getStringExtra("taskDescription") ?: ""
        val taskDateStr    = intent.getStringExtra("taskDeadlineDate")    // "yyyy-MM-dd" или null
        val taskTimeStr    = intent.getStringExtra("taskDeadlineTime")    // "HH:mm:ss" или null

        setContent {
            EditTaskScreen(
                taskId = taskId,
                initialName = taskName,
                initialDescription = taskDesc,
                initialDeadlineDate = taskDateStr?.let { LocalDate.parse(it) },
                initialDeadlineTime = taskTimeStr?.let { LocalTime.parse(it) },
                onFinish = { shouldRefresh ->
                    if (shouldRefresh) finish()
                }
            )
        }
    }
}

@Composable
private fun EditTaskScreen(
    taskId: Long,
    initialName: String,
    initialDescription: String,
    initialDeadlineDate: LocalDate?,
    initialDeadlineTime: LocalTime?,
    onFinish: (Boolean) -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf(initialName) }
    var description by remember { mutableStateOf(initialDescription) }
    var deadlineDate by remember { mutableStateOf<LocalDate?>(initialDeadlineDate) }
    var deadlineTime by remember { mutableStateOf<LocalTime?>(initialDeadlineTime) }

    var isSaving by remember { mutableStateOf(false) }
    var isDeleting by remember { mutableStateOf(false) }

    // Форматы отображения
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    // DatePickerDialog и TimePickerDialog
    val todayCal = java.util.Calendar.getInstance()
    val datePicker = remember {
        android.app.DatePickerDialog(
            context,
            { _, y, m, d -> deadlineDate = LocalDate.of(y, m + 1, d) },
            todayCal.get(java.util.Calendar.YEAR),
            todayCal.get(java.util.Calendar.MONTH),
            todayCal.get(java.util.Calendar.DAY_OF_MONTH)
        )
    }
    val nowCal = java.util.Calendar.getInstance()
    val timePicker = remember {
        android.app.TimePickerDialog(
            context,
            { _, h, min -> deadlineTime = LocalTime.of(h, min) },
            nowCal.get(java.util.Calendar.HOUR_OF_DAY),
            nowCal.get(java.util.Calendar.MINUTE),
            true
        )
    }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Редактировать задачу", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        // Поле «Название»
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Название") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Поле «Описание»
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Описание") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Дата дедлайна
        OutlinedTextField(
            value = deadlineDate?.format(dateFormatter) ?: "",
            onValueChange = { },
            readOnly = true,
            label = { Text("Дата дедлайна") },
            trailingIcon = {
                IconButton(onClick = { datePicker.show() }) {
                    Icon(Icons.Default.Edit, contentDescription = "Выбрать дату")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Время дедлайна
        OutlinedTextField(
            value = deadlineTime?.format(timeFormatter) ?: "",
            onValueChange = { },
            readOnly = true,
            label = { Text("Время дедлайна") },
            trailingIcon = {
                IconButton(onClick = { timePicker.show() }) {
                    Icon(Icons.Default.Edit, contentDescription = "Выбрать время")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Кнопки «Сохранить» и «Удалить»
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Кнопка «Сохранить»
            Button(
                onClick = {
                    if (name.isBlank()) {
                        Toast.makeText(context, "Введите название задачи", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    isSaving = true
                    coroutineScope.launch {
                        val token = context
                            .getSharedPreferences("auth", MODE_PRIVATE)
                            .getString("jwt", "") ?: ""
                        val request = TaskRequest(
                            name = name,
                            description = description.ifBlank { null },
                            deadlineDate = deadlineDate,
                            deadlineTime = deadlineTime,
                            remind = false,       // напоминания убраны
                            remindTime = null     // напоминания убраны
                        )
                        // Предполагается реализация метода updateTask(id, request) в вашем ApiService
                        val response = RetrofitInstance.api.updateTask(taskId, request, "Bearer $token")
                        withContext(Dispatchers.Main) {
                            isSaving = false
                            if (response.isSuccessful) {
                                Toast.makeText(context, "Задача обновлена", Toast.LENGTH_SHORT).show()
                                onFinish(true)
                            } else {
                                Toast.makeText(context, "Ошибка: ${response.code()}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                enabled = !isSaving,
                modifier = Modifier.weight(1f)
            ) {
                if (isSaving) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                } else {
                    Text("Сохранить")
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Кнопка «Удалить»
            OutlinedButton(
                onClick = {
                    isDeleting = true
                    coroutineScope.launch {
                        val token = context
                            .getSharedPreferences("auth", MODE_PRIVATE)
                            .getString("jwt", "") ?: ""
                        val deleteResp = RetrofitInstance.api.deleteTask(taskId, "Bearer $token")
                        withContext(Dispatchers.Main) {
                            isDeleting = false
                            if (deleteResp.isSuccessful) {
                                Toast.makeText(context, "Задача удалена", Toast.LENGTH_SHORT).show()
                                onFinish(true)
                            } else {
                                Toast.makeText(context, "Ошибка при удалении: ${deleteResp.code()}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                enabled = !isDeleting,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                if (isDeleting) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                } else {
                    Text("Удалить")
                }
            }
        }
    }
}
