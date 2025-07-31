package backend.workshop.controller

import backend.workshop.model.request.SubtaskRequest
import backend.workshop.model.response.SubtaskResponse

interface SubtaskController {
    fun create(request: SubtaskRequest, token: String): SubtaskResponse
    fun getById(id: Long, token: String): SubtaskResponse
    fun update(id: Long, request: SubtaskRequest, token: String): SubtaskResponse
    fun delete(id: Long, token: String)
    fun list(token: String): List<SubtaskResponse>
}