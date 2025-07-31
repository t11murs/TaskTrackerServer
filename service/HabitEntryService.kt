package backend.workshop.service

import backend.workshop.model.request.HabitEntryRequest
import backend.workshop.model.response.HabitEntryResponse

interface HabitEntryService {
    fun create(request: HabitEntryRequest, userId: Long): HabitEntryResponse
    fun getById(id: Long, userId: Long): HabitEntryResponse
    fun update(id: Long, request: HabitEntryRequest, userId: Long): HabitEntryResponse
    fun delete(id: Long, userId: Long)
    fun list(userId: Long): List<HabitEntryResponse>
}