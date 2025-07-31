package com.example.habittracker.ui.screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.instance.RetrofitInstance
import model.request.TaskRequest
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

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
    var remind by remember { mutableStateOf(false) }
    var remindTime by remember { mutableStateOf("") }

    val dateFormatter = java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val timeFormatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm")

    val context = LocalContext.current

    // Создаем DatePicker и TimePicker
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

        // Дата
        OutlinedTextField(
            value = deadlineDate?.format(dateFormatter) ?: "",
            onValueChange = { },
            readOnly = true,
            label = { Text("Дата дедлайна") },
            trailingIcon = {
                IconButton(onClick = { datePicker.show() }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Выбрать дату"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Время
        OutlinedTextField(
            value = deadlineTime?.format(timeFormatter) ?: "",
            onValueChange = { },
            readOnly = true,
            label = { Text("Время дедлайна") },
            trailingIcon = {
                IconButton(onClick = { timePicker.show() }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Выбрать время"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = remind, onCheckedChange = { remind = it })
            Spacer(modifier = Modifier.width(4.dp))
            Text("Напомнить")
        }
        if (remind) {
            OutlinedTextField(
                value = remindTime,
                onValueChange = { remindTime = it },
                label = { Text("За сколько минут") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (name.isBlank()) {
                    Toast.makeText(context, "Введите название задачи", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                val request = TaskRequest(
                    name = name,
                    description = description.ifBlank { null },
                    deadlineDate = deadlineDate,
                    deadlineTime = deadlineTime,
                    remind = remind,
                    remindTime = remindTime.toIntOrNull()
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
