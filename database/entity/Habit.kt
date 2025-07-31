package backend.workshop.database.entity

import backend.workshop.model.enums.GoalType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "habits")
class Habit(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var userId: Long,

    @Column(length = 125)
    var description: String?,

    @Column(nullable = false)
    var goalType: GoalType,

    @Column
    var goalCount: Int?,

    @Column(nullable = false)
    var remind: Boolean,

    @Column
    var remindTime: Int?

): AbstractEntity()