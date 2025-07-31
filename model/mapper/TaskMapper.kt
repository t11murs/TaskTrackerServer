package backend.workshop.model.mapper

import backend.workshop.database.entity.Task
import backend.workshop.model.request.TaskRequest
import backend.workshop.model.response.TaskResponse
import org.springframework.stereotype.Component

@Component
class TaskMapper {

    fun asEntity(request: TaskRequest, userId: Long) = Task (
        name = request.name,
        description = request.description,
        userId = userId,
        deadlineDate = request.deadlineDate,
        deadlineTime = request.deadlineTime,
        remind = request.remind,
        remindTime = request.remindTime,
    )

    fun asResponse(task: Task) = TaskResponse(
        name = task.name,
        id = task.id,
        createdAt = task.createdAt,
        description = task.description,
        userId = task.userId,
        deadlineDate = task.deadlineDate,
        deadlineTime = task.deadlineTime,
        remind = task.remind,
        remindTime = task.remindTime,
    )

    fun update(task: Task, request: TaskRequest): Task {
        task.name = request.name
        task.description = request.description
        task.deadlineDate = request.deadlineDate
        task.deadlineTime = request.deadlineTime
        task.remind = request.remind
        task.remindTime = request.remindTime
        return task
    }

    fun asListResponse(tasks: Iterable<Task>) = tasks.map{ asResponse(it) }

}