package com.example.habittracker.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    object Habits : Screen("habits", Icons.Default.List, "Привычки")
    object Tasks  : Screen("tasks",  Icons.Default.Edit, "Задачи")
    object Profile: Screen("profile", Icons.Default.Person, "Профиль")
}