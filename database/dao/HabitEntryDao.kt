package backend.workshop.database.dao

import backend.workshop.database.entity.HabitEntry

interface HabitEntryDao: CommonDao<HabitEntry> {
    fun findByHabitIdIn(habitIds: List<Long>): MutableList<HabitEntry>
}