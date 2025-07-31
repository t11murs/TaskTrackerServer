package backend.workshop.controller

import backend.workshop.model.request.HabitRequest
import backend.workshop.model.response.HabitResponse

interface HabitController {
    fun create(request: HabitRequest, token: String): HabitResponse
    fun getById(id: Long, token: String): HabitResponse
    fun update(id: Long, request: HabitRequest, token: String): HabitResponse
    fun delete(id: Long, token: String)
    fun list(token: String): List<HabitResponse>
}