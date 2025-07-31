package backend.workshop.service

import backend.workshop.database.entity.Subtask
import backend.workshop.model.request.SubtaskRequest
import backend.workshop.model.response.SubtaskResponse

interface SubtaskService {
    fun create(request: SubtaskRequest, userId: Long): SubtaskResponse
    fun update(id: Long, request: SubtaskRequest, userId: Long): SubtaskResponse
    fun delete(id: Long, userId: Long)
    fun getById(id: Long, userId: Long): SubtaskResponse
    fun list(userId: Long): List<SubtaskResponse>
}
