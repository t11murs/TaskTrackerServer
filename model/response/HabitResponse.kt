package backend.workshop.model.response

import backend.workshop.model.enums.GoalType
import java.time.LocalDateTime

class HabitResponse(
    override val id: Long,
    override val createdAt: LocalDateTime,
    val name: String,
    val description: String?,
    val userId: Long,
    val goalType: GoalType,
    val goalCount: Int?,
    val remind: Boolean,
    val remindTime: Int?
) : EntityResponse

