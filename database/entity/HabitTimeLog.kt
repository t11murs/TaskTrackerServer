package backend.workshop.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalTime

@Entity
@Table(name = "habit_time_log")
class HabitTimeLog(

    @Column(nullable = false)
    var habitId: Long,

    @Column
    var startTime: LocalTime = LocalTime.now(),

    @Column
    val endTime: LocalTime = LocalTime.now(),

    @Column
    var durationMinutes: Int = 0

) : AbstractEntity()