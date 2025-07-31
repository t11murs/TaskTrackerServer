package backend.workshop.database.dao

import backend.workshop.database.entity.Habit
import backend.workshop.database.entity.Task

interface HabitDao: CommonDao<Habit> {
    fun findEntityById(id: Long): Habit?
    fun findByUserId(userId: Long): List<Habit>
    fun findByIdAndUserId(id: Long, userId: Long): Habit?
}