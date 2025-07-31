package backend.workshop.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "subtask")
class Subtask(
    @Column(nullable = false)
    var taskId: Long,

    @Column(nullable = false)
    var name: String,

    @Column(length = 125)
    var description: String?,

    @Column
    var completed: Boolean = false,

    @Column
    var trackTime: Boolean = false,

): AbstractEntity()