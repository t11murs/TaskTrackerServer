package backend.workshop.model.response

import java.time.LocalDateTime

interface EntityResponse {
    val id: Long
    val createdAt: LocalDateTime
}