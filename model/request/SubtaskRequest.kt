package backend.workshop.model.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length

class SubtaskRequest(
    @field:NotBlank
    var name: String,

    @field:Length(max = 125)
    var description: String? = null,

    @field:NotNull
    var taskId: Long,

    var trackTime: Boolean = false,

    var completed: Boolean = false,
)