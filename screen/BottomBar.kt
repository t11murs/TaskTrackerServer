package com.example.habittracker.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.habittracker.ui.screen.Screen.Habits
import com.example.habittracker.ui.screen.Screen.Tasks
import com.example.habittracker.ui.screen.Screen.Profile
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navController: NavHostController) {
    data class Screen(
        val route: String,
        val label: String,
        @DrawableRes val iconRes: Int
    )

    val screens = listOf(
        Screen("habits", "Привычки", android.R.drawable.ic_menu_agenda),
        Screen("tasks",  "Задачи",   android.R.drawable.ic_menu_today)
    )

    val items = listOf(Habits, Tasks,Profile)
    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { screen ->
            NavigationBar {
                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.iconRes),
                                contentDescription = screen.label
                            )
                        },
                        label    = { Text(screen.label) },
                        selected = currentRoute == screen.route,
                        onClick  = { navController.navigate(screen.route) }
                    )
                }
            }
        }
    }
}