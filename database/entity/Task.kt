package backend.workshop.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalTime

@Entity
@Table(name = "task")
class Task(
    @Column(length = 35, nullable = false)
    var name: String,

    @Column(length = 125)
    var description: String?,

    @Column(nullable = false)
    val userId: Long,

    @Column
    var deadlineDate: LocalDate?,

    @Column
    var deadlineTime: LocalTime?,

    @Column(nullable = false)
    var completed: Boolean = false,

    @Column
    var totalDurationMinutes: Int = 0,

    @Column(nullable = false)
    var remind: Boolean,

    @Column
    var remindTime: Int?


) : AbstractEntity()


