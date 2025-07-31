package backend.workshop.service.impl

import backend.workshop.database.dao.HabitDao
import backend.workshop.database.dao.TaskDao
import backend.workshop.model.mapper.TaskMapper
import backend.workshop.model.request.TaskRequest
import backend.workshop.model.response.TaskResponse
import backend.workshop.service.TaskService

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import jakarta.persistence.EntityNotFoundException

@Service
class TaskServiceImpl(
    private val dao: TaskDao,
    private val mapper: TaskMapper
) : TaskService {

    @Transactional
    override fun createForUser(request: TaskRequest, userId: Long): TaskResponse {
        val habit = mapper.asEntity(request, userId)
        dao.save(habit)
        return mapper.asResponse(habit)
    }

    override fun getByIdForUser(id: Long, userId: Long): TaskResponse {
        val habit = dao.findByIdAndUserId(id, userId)
            ?: throw EntityNotFoundException("Habit not found or does not belong to user")
        return mapper.asResponse(habit)
    }

    override fun listTasksForUser(userId: Long): List<TaskResponse> {
        return mapper.asListResponse(dao.findByUserId(userId))
    }

    @Transactional
    override fun updateForUser(id: Long, request: TaskRequest, userId: Long): TaskResponse {
        val habit = dao.findByIdAndUserId(id, userId)
            ?: throw EntityNotFoundException("Habit not found or access denied")
        val updated = mapper.update(habit, request)
        return mapper.asResponse(updated)
    }

    @Transactional
    override fun deleteByIdForUser(id: Long, userId: Long) {
        val habit = dao.findByIdAndUserId(id, userId)
            ?: throw EntityNotFoundException("Habit not found or access denied")
        dao.delete(habit)
    }
}
