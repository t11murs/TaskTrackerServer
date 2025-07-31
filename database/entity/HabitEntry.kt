package backend.workshop.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalTime

@Entity
@Table(name = "habit_entries")
class HabitEntry(
    @Column(nullable = false)
    var habitId: Long,

    @Column
    var date: LocalDate,

    @Column
    var time: LocalTime,

    @Column(nullable = false)
    var completed: Boolean = false


):AbstractEntity()