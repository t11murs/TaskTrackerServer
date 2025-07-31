package backend.workshop.service

import backend.workshop.model.request.TaskRequest
import backend.workshop.model.response.TaskResponse

interface TaskService {
    fun createForUser(request: TaskRequest, userId: Long): TaskResponse
    fun getByIdForUser(id: Long, userId: Long): TaskResponse
    fun listTasksForUser(userId: Long): List<TaskResponse>
    fun updateForUser(id: Long, request: TaskRequest, userId: Long): TaskResponse
    fun deleteByIdForUser(id: Long, userId: Long)
}