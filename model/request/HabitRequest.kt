package backend.workshop.model.request

import backend.workshop.model.enums.GoalType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length

class HabitRequest(
    @field:NotBlank
    var name: String,

    @field:NotNull
    var userId: Long,

    @field:Length(max = 125)
    var description: String? = null,

    var goalType: GoalType,

    var goalCount: Int? = null,

    var remind: Boolean = false,

    var remindTime: Int? = null

)