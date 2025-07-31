package com.example.habittracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import model.instance.RetrofitInstance
import model.request.TaskRequest
import model.response.TaskResponse

class TaskViewModel : ViewModel() {

    // JWT-токен назначается извне (в TaskActivity и NewTaskActivity)
    var jwt: String = ""

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _tasks = MutableStateFlow<List<TaskResponse>>(emptyList())
    val tasks: StateFlow<List<TaskResponse>> = _tasks.asStateFlow()

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error.asSharedFlow()

    /** Загружает список задач текущего пользователя */
    fun loadTasks() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val resp = RetrofitInstance.api.getTasks("Bearer $jwt")
                if (resp.isSuccessful) {
                    _tasks.value = resp.body() ?: emptyList()
                } else {
                    _error.emit("Ошибка загрузки задач: ${resp.code()}")
                }
            } catch (e: Exception) {
                _error.emit("Сетевая ошибка: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Создаёт новую задачу.
     * При успехе вызывает onSuccess(), чтобы активность могла перезагрузить список.
     */
    fun createTask(request: TaskRequest, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val resp = RetrofitInstance.api.createTask(request, "Bearer $jwt")
                if (resp.isSuccessful) {
                    onSuccess()
                } else {
                    _error.emit("Ошибка создания задачи: ${resp.code()}")
                }
            } catch (e: Exception) {
                _error.emit("Сетевая ошибка: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
