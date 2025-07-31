package model.response

data class UserResponse(
    val id: Long,
    val createdAt: String,
    val name: String,
    val email: String,
    val tasks: List<TaskResponse>? = emptyList()
)