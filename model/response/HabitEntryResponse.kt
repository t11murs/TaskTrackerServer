package backend.workshop.model.response

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class HabitEntryResponse(
    override val id: Long,
    override val createdAt: LocalDateTime,
    val habitId: Long,
    val date: LocalDate?,
    val time: LocalTime?,
    val completed: Boolean
) : EntityResponse
