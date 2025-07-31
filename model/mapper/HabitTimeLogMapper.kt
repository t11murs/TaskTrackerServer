package backend.workshop.model.mapper

import backend.workshop.database.entity.HabitTimeLog
import backend.workshop.model.request.HabitTimeLogRequest
import backend.workshop.model.response.HabitTimeLogResponse
import org.springframework.stereotype.Component
import java.time.LocalTime

@Component
class HabitTimeLogMapper {

    fun asEntity(request: HabitTimeLogRequest): HabitTimeLog {
        val entity = HabitTimeLog(
            habitId = request.habitId
        )
        entity.startTime = request.startTime ?: LocalTime.now()
        entity.durationMinutes = request.durationMinutes
        return entity
    }

    fun asResponse(log: HabitTimeLog): HabitTimeLogResponse {
        return HabitTimeLogResponse(
            id = log.id,
            startTime = log.startTime,
            endTime = log.endTime,
            durationMinutes = log.durationMinutes,
            habitId = log.habitId
        )
    }

    fun update(log: HabitTimeLog, request: HabitTimeLogRequest): HabitTimeLog {
        log.startTime = request.startTime ?: log.startTime
        log.durationMinutes = request.durationMinutes
        return log
    }

    fun asListResponse(logs: Iterable<HabitTimeLog>) = logs.map { asResponse(it) }
}