package backend.workshop.database.dao

import backend.workshop.database.entity.Subtask

interface SubtaskDao: CommonDao<Subtask> {
    fun save(task: Subtask): Subtask
    fun findByTaskIdIn(taskIds: List<Long>): List<Subtask>
}