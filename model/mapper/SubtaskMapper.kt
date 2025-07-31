package backend.workshop.model.mapper

import backend.workshop.database.entity.Subtask
import backend.workshop.model.request.SubtaskRequest
import backend.workshop.model.response.SubtaskResponse
import org.springframework.stereotype.Component

@Component
class SubtaskMapper {

    fun asEntity(request: SubtaskRequest) = Subtask(
        taskId = request.taskId,
        name = request.name,
        description = request.description,
        completed = request.completed,
        trackTime = request.trackTime
    )

    fun asResponse(subtask: Subtask) = SubtaskResponse(
        id = subtask.id,
        createdAt = subtask.createdAt,
        taskId = subtask.taskId,
        name = subtask.name,
        description = subtask.description,
        completed = subtask.completed,
        trackTime = subtask.trackTime
    )

    fun update(subtask: Subtask, request: SubtaskRequest): Subtask {
        subtask.name = request.name
        subtask.description = request.description
        subtask.completed = request.completed
        subtask.trackTime = request.trackTime
        return subtask
    }

    fun asListResponse(subtasks: Iterable<Subtask>) = subtasks.map { asResponse(it) }
}
