package backend.workshop.model.response

import java.time.LocalDateTime

class UserResponse(
    override val id: Long,
    override val createdAt: LocalDateTime,
    val name: String,
    val email: String,
): EntityResponse