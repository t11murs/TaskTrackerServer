package backend.workshop.model.mapper

import backend.workshop.database.entity.SubtaskTimeLog
import backend.workshop.model.request.SubtaskTimeLogRequest
import backend.workshop.model.response.SubtaskTimeLogResponse
import org.springframework.stereotype.Component
import java.time.LocalTime

@Component
class SubtaskTimeLogMapper {

    fun asEntity(request: SubtaskTimeLogRequest): SubtaskTimeLog {
        val entity = SubtaskTimeLog(
            subtaskId = request.subtaskId
        )
        entity.startTime = request.startTime ?: LocalTime.now()
        entity.durationMinutes = request.durationMinutes
        return entity
    }

    fun asResponse(log: SubtaskTimeLog): SubtaskTimeLogResponse {
        return SubtaskTimeLogResponse(
            id = log.id,
            startTime = log.startTime,
            endTime = log.endTime,
            durationMinutes = log.durationMinutes,
            subtaskId = log.subtaskId
        )
    }

    fun update(log: SubtaskTimeLog, request: SubtaskTimeLogRequest): SubtaskTimeLog {
        log.startTime = request.startTime ?: log.startTime
        log.durationMinutes = request.durationMinutes
        return log
    }

    fun asListResponse(logs: Iterable<SubtaskTimeLog>) = logs.map { asResponse(it) }
}
