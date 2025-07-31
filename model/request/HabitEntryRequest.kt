package backend.workshop.model.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate
import java.time.LocalTime

class HabitEntryRequest(
    @field:NotNull
    var habitId: Long,

    var date: LocalDate? = null,

    var time: LocalTime? = null,

    var completed: Boolean = false
)