package backend.workshop.service

import backend.workshop.model.request.SubtaskTimeLogRequest
import backend.workshop.model.response.SubtaskTimeLogResponse

interface SubtaskTimeLogService {
    fun create(request: SubtaskTimeLogRequest, userId: Long): SubtaskTimeLogResponse
    fun getById(id: Long, userId: Long): SubtaskTimeLogResponse
    fun delete(id: Long, userId: Long)
    fun list(userId: Long): List<SubtaskTimeLogResponse>
}