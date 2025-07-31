package backend.workshop.controller

import backend.workshop.model.message.DeletedMessage
import backend.workshop.model.request.TaskRequest
import backend.workshop.model.response.TaskResponse

interface TaskController {
    fun list(token: String): List<TaskResponse>
    fun getById(id: Long, token: String): TaskResponse
    fun create(request: TaskRequest, token: String): TaskResponse
    fun update(id: Long, request: TaskRequest, token: String): TaskResponse
    fun delete(id: Long,  token: String): DeletedMessage
}