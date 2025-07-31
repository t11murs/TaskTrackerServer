package backend.workshop.controller

import backend.workshop.model.request.HabitEntryRequest
import backend.workshop.model.response.HabitEntryResponse

interface HabitEntryController {
    fun create(request: HabitEntryRequest, token: String): HabitEntryResponse
    fun getById(id: Long, token: String): HabitEntryResponse
    fun update(id: Long, request: HabitEntryRequest, token: String): HabitEntryResponse
    fun delete(id: Long, token: String)
    fun list(token: String): List<HabitEntryResponse>
}