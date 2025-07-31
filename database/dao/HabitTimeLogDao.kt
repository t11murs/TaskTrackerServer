package backend.workshop.database.dao

import backend.workshop.database.entity.HabitTimeLog

interface HabitTimeLogDao: CommonDao<HabitTimeLog>{
    fun save(habitTimeLog: HabitTimeLog)
    fun findByHabitIdIn(habitIds: List<Long>): List<HabitTimeLog>
}