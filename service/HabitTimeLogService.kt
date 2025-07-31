package backend.workshop.service

import backend.workshop.model.request.HabitTimeLogRequest
import backend.workshop.model.response.HabitTimeLogResponse

interface HabitTimeLogService {
    fun create(request: HabitTimeLogRequest, userId: Long): HabitTimeLogResponse
    fun getById(id: Long, userId: Long): HabitTimeLogResponse
    fun delete(id: Long, userId: Long)
    fun list(userId: Long): List<HabitTimeLogResponse>
}