package backend.workshop.service.impl

import backend.workshop.database.dao.SubtaskDao
import backend.workshop.database.dao.SubtaskTimeLogDao
import backend.workshop.database.dao.TaskDao
import backend.workshop.model.mapper.SubtaskTimeLogMapper
import backend.workshop.model.request.SubtaskTimeLogRequest
import backend.workshop.model.response.SubtaskTimeLogResponse
import backend.workshop.service.SubtaskTimeLogService
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.access.AccessDeniedException

@Service
class SubtaskTimeLogServiceImpl(
    private val logDao: SubtaskTimeLogDao,
    private val subtaskDao: SubtaskDao,
    private val taskDao: TaskDao,
    private val mapper: SubtaskTimeLogMapper
) : SubtaskTimeLogService {

    @Transactional
    override fun create(request: SubtaskTimeLogRequest, userId: Long): SubtaskTimeLogResponse {
        val subtask = subtaskDao.findById(request.subtaskId)
            .orElseThrow { throw EntityNotFoundException("Subtask not found") }

        val task = taskDao.findById(subtask.taskId)
            .orElseThrow { throw EntityNotFoundException("Task not found") }

        if (task.userId != userId) {
            throw AccessDeniedException("You do not have access to this subtask")
        }

        val log = mapper.asEntity(request)
        logDao.save(log)
        return mapper.asResponse(log)
    }

    @Transactional(readOnly = true)
    override fun getById(id: Long, userId: Long): SubtaskTimeLogResponse {
        val log = logDao.findById(id)
            .orElseThrow { throw EntityNotFoundException("SubtaskTimeLog not found") }

        val subtask = subtaskDao.findById(log.subtaskId)
            .orElseThrow { throw EntityNotFoundException("Subtask not found") }

        val task = taskDao.findById(subtask.taskId)
            .orElseThrow { throw EntityNotFoundException("Task not found") }

        if (task.userId != userId) {
            throw AccessDeniedException("You do not have access to this subtask time log")
        }

        return mapper.asResponse(log)
    }

    @Transactional
    override fun delete(id: Long, userId: Long) {
        val log = logDao.findById(id)
            .orElseThrow { throw EntityNotFoundException("SubtaskTimeLog not found") }

        val subtask = subtaskDao.findById(log.subtaskId)
            .orElseThrow { throw EntityNotFoundException("Subtask not found") }

        val task = taskDao.findById(subtask.taskId)
            .orElseThrow { throw EntityNotFoundException("Task not found") }

        if (task.userId != userId) {
            throw AccessDeniedException("You do not have access to this subtask time log")
        }

        logDao.delete(log)
    }

    @Transactional(readOnly = true)
    override fun list(userId: Long): List<SubtaskTimeLogResponse> {
        val taskIds = taskDao.findByUserId(userId).map { it.id }
        val subtaskIds = subtaskDao.findByTaskIdIn(taskIds).map { it.id }
        val logs = logDao.findBySubtaskIdIn(subtaskIds)
        return mapper.asListResponse(logs)
    }
}
