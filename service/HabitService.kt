package backend.workshop.service

import backend.workshop.model.request.HabitRequest
import backend.workshop.model.response.HabitResponse

interface HabitService {
    fun create(request: HabitRequest, userId: Long): HabitResponse
    fun update(id: Long, request: HabitRequest, userId: Long): HabitResponse
    fun delete(id: Long, userId: Long)
    fun getById(id: Long, userId: Long): HabitResponse
    fun list(userId: Long): List<HabitResponse>
}