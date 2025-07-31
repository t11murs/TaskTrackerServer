package backend.workshop.database.dao

import backend.workshop.database.entity.Task

interface TaskDao: CommonDao<Task> {
    fun findEntityById(id: Long): Task?
    fun findByUserId(userId: Long): List<Task>
    fun findByIdAndUserId(id: Long, userId: Long): Task?
}