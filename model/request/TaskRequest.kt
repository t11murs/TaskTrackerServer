package model.request

import androidx.annotation.NonNull
import androidx.annotation.Size
import androidx.annotation.IntRange
import java.time.LocalDate
import java.time.LocalTime

data class TaskRequest(
    @field:NonNull
    var name: String,

    @field:Size(max = 125)
    var description: String? = null,

    var deadlineDate: LocalDate? = null,
    var deadlineTime: LocalTime? = null,

    var remind: Boolean = false,

    @field:IntRange(from = 0)
    var remindTime: Int? = null
)
