package model.response

import java.time.LocalDate
import java.time.LocalTime

class TaskResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val userId: Long,
    var deadlineDate: LocalDate?,
    var deadlineTime: LocalTime?,
    var completed: Boolean = false,
    var totalDurationMinutes: Int = 0,
    var remind: Boolean,
    var remindTime: Int?
)