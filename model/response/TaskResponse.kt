package backend.workshop.model.response

import jakarta.persistence.Column
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TaskResponse(
    override val id: Long,
    override val createdAt: LocalDateTime,
    val name: String,
    val description: String?,
    val userId: Long,
    var deadlineDate: LocalDate?,
    var deadlineTime: LocalTime?,
    var completed: Boolean = false,
    var totalDurationMinutes: Int = 0,
    var remind: Boolean,
    var remindTime: Int?
): EntityResponse

