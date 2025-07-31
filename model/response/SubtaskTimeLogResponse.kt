package backend.workshop.model.response

import java.time.LocalDateTime
import java.time.LocalTime

class SubtaskTimeLogResponse(
    val id: Long,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val durationMinutes: Int,
    val subtaskId: Long
)