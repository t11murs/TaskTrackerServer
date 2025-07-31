package com.example.habittracker.ui.screen

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.habittracker.ui.activity.EditTaskActivity
import model.response.TaskResponse

@Composable
fun TaskScreen(
    tasks: List<TaskResponse> = emptyList(),
    onAddClick: () -> Unit = {}
) {
    // Захватываем контекст здесь, в теле @Composable
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Задачи:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        if (tasks.isEmpty()) {
            Text("Нет задач", style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(tasks) { task ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Название задачи
                                Text(task.name, style = MaterialTheme.typography.bodyLarge)

                                // Если выполнена — галочка
                                if (task.completed) {
                                    Text("✔", style = MaterialTheme.typography.bodyLarge)
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            // Описание (если есть)
                            task.description?.let { desc ->
                                Text(desc, style = MaterialTheme.typography.bodyMedium)
                                Spacer(modifier = Modifier.height(4.dp))
                            }

                            // Дата и время (если есть)
                            task.deadlineDate?.let { dateStr ->
                                Text("Дата: $dateStr", style = MaterialTheme.typography.bodySmall)
                            }
                            task.deadlineTime?.let { timeStr ->
                                Text("Время: $timeStr", style = MaterialTheme.typography.bodySmall)
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            // Кнопка «Редактировать» справа
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = {
                                    // Используем context, захваченный выше
                                    val intent = Intent(context, EditTaskActivity::class.java).apply {
                                        putExtra("taskId", task.id)
                                        putExtra("taskName", task.name)
                                        putExtra("taskDescription", task.description)
                                        putExtra("taskDeadlineDate", task.deadlineDate)
                                        putExtra("taskDeadlineTime", task.deadlineTime)
                                        putExtra("taskRemind", task.remind)
                                        putExtra("taskRemindTime", task.remindTime ?: -1)
                                    }
                                    context.startActivity(intent)
                                }) {
                                    Icon(
                                        imageVector = androidx.compose.material.icons.Icons.Default.Edit,
                                        contentDescription = "Редактировать"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onAddClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Добавить задачу")
        }
    }
}
