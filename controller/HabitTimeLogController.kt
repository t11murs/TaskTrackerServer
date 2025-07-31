package backend.workshop.controller

import backend.workshop.model.request.HabitTimeLogRequest
import backend.workshop.model.response.HabitTimeLogResponse

interface HabitTimeLogController {
    fun create(request: HabitTimeLogRequest, token: String): HabitTimeLogResponse
    fun getById(id: Long, token: String): HabitTimeLogResponse
    fun delete(id: Long, token: String)
    fun list(token: String): List<HabitTimeLogResponse>
}
