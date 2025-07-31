package backend.workshop.model.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import java.time.LocalDate
import java.time.LocalTime

data class TaskRequest(
    @field:NotBlank
    var name: String,

    @field:NotNull
    var userId: Long,

    @field:Length(max = 125)
    var description: String? = null,

    var deadlineDate: LocalDate? = null,
    var deadlineTime: LocalTime? = null,

    var remind: Boolean = false,

    var remindTime: Int? = null
)
