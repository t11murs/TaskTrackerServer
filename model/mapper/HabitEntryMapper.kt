package backend.workshop.model.mapper

import backend.workshop.database.entity.HabitEntry
import backend.workshop.model.request.HabitEntryRequest
import backend.workshop.model.response.HabitEntryResponse
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalTime

@Component
class HabitEntryMapper {

    fun asEntity(request: HabitEntryRequest) = HabitEntry(
        habitId = request.habitId,
        date = request.date ?: LocalDate.now(),
        time = request.time ?: LocalTime.now(),
        completed = request.completed
    )

    fun asResponse(entry: HabitEntry) = HabitEntryResponse(
        id = entry.id,
        createdAt = entry.createdAt,
        habitId = entry.habitId,
        date = entry.date,
        time = entry.time,
        completed = entry.completed
    )

    fun update(entry: HabitEntry, request: HabitEntryRequest): HabitEntry {
        entry.date = request.date ?: entry.date
        entry.time = request.time ?: entry.time
        entry.completed = request.completed
        return entry
    }

    fun asListResponse(entries: Iterable<HabitEntry>) = entries.map { asResponse(it) }
}
