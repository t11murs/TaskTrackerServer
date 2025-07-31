package backend.workshop.database.dao

import backend.workshop.database.entity.SubtaskTimeLog

interface SubtaskTimeLogDao: CommonDao<SubtaskTimeLog>{
    fun findBySubtaskIdIn(subtaskIds: List<Long>): List<SubtaskTimeLog>
}