package backend.workshop.service.impl

import backend.workshop.database.dao.SubtaskDao
import backend.workshop.database.dao.TaskDao
import backend.workshop.model.mapper.SubtaskMapper
import backend.workshop.model.request.SubtaskRequest
import backend.workshop.model.response.SubtaskResponse
import backend.workshop.service.SubtaskService

import jakarta.persistence.EntityNotFoundException
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SubtaskServiceImpl(
    private val subtaskDao: SubtaskDao,
    private val taskDao: TaskDao,
    private val mapper: SubtaskMapper
) : SubtaskService {

    @Transactional
    override fun create(request: SubtaskRequest, userId: Long): SubtaskResponse {
        val task = taskDao.findById(request.taskId)
            .orElseThrow { throw EntityNotFoundException("Task not found") }

        if (task.userId != userId) {
            throw AccessDeniedException("You do not have permission to create subtasks for this task")
        }

        val subtask = mapper.asEntity(request)
        subtaskDao.save(subtask)
        return mapper.asResponse(subtask)
    }

    @Transactional
    override fun update(id: Long, request: SubtaskRequest, userId: Long): SubtaskResponse {
        val subtask = subtaskDao.findById(id)
            .orElseThrow { throw EntityNotFoundException("Subtask not found") }

        val task = taskDao.findById(subtask.taskId)
            .orElseThrow { throw EntityNotFoundException("Task not found") }

        if (task.userId != userId) {
            throw AccessDeniedException("You do not have access to this subtask")
        }

        val updated = mapper.update(subtask, request)
        return mapper.asResponse(updated)
    }

    @Transactional
    override fun delete(id: Long, userId: Long) {
        val subtask = subtaskDao.findById(id)
            .orElseThrow { throw EntityNotFoundException("Subtask not found") }

        val task = taskDao.findById(subtask.taskId)
            .orElseThrow { throw EntityNotFoundException("Task not found") }

        if (task.userId != userId) {
            throw AccessDeniedException("You do not have permission to delete this subtask")
        }

        subtaskDao.delete(subtask)
    }

    @Transactional(readOnly = true)
    override fun getById(id: Long, userId: Long): SubtaskResponse {
        val subtask = subtaskDao.findById(id)
            .orElseThrow { throw EntityNotFoundException("Subtask not found") }

        val task = taskDao.findById(subtask.taskId)
            .orElseThrow { throw EntityNotFoundException("Task not found") }

        if (task.userId != userId) {
            throw AccessDeniedException("You do not have access to this subtask")
        }

        return mapper.asResponse(subtask)
    }

    @Transactional(readOnly = true)
    override fun list(userId: Long): List<SubtaskResponse> {
        // Находим все задачи пользователя
        val tasks = taskDao.findByUserId(userId)

        if (tasks.isEmpty()) {
            return emptyList()
        }

        // Извлекаем taskIds
        val taskIds = tasks.map { it.id }

        // Находим все сабтаски по taskIds
        val subtasks = subtaskDao.findByTaskIdIn(taskIds)

        // Маппим сабтаски в респонсы
        return mapper.asListResponse(subtasks)
    }
}
