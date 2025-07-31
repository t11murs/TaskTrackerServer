package backend.workshop.controller

import backend.workshop.model.request.SubtaskTimeLogRequest
import backend.workshop.model.response.SubtaskTimeLogResponse

interface SubtaskTimeLogController {
    fun create(request: SubtaskTimeLogRequest, token: String): SubtaskTimeLogResponse
    fun getById(id: Long, token: String): SubtaskTimeLogResponse
    fun delete(id: Long, token: String)
    fun list(token: String): List<SubtaskTimeLogResponse>
}
