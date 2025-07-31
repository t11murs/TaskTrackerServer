package backend.workshop.model.response

import java.time.LocalDateTime

class SubtaskResponse(
    override val id: Long,
    override val createdAt: LocalDateTime,
    val taskId: Long,
    val name: String,
    val description: String?,
    val completed: Boolean,
    val trackTime: Boolean
) : EntityResponse

