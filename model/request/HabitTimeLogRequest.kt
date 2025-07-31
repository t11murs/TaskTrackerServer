package backend.workshop.model.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalTime

class HabitTimeLogRequest(
    @field:NotNull
    var habitId: Long,

    var startTime: LocalTime? = null,

    var endTime: LocalTime? = null,

    var durationMinutes: Int = 0
)
