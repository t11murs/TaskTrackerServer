package backend.workshop.service.impl

import backend.workshop.database.dao.HabitDao
import backend.workshop.database.dao.HabitTimeLogDao
import backend.workshop.model.mapper.HabitTimeLogMapper
import backend.workshop.model.request.HabitTimeLogRequest
import backend.workshop.model.response.HabitTimeLogResponse
import backend.workshop.service.HabitTimeLogService
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.access.AccessDeniedException

@Service
class HabitTimeLogServiceImpl(
    private val logDao: HabitTimeLogDao,
    private val habitDao: HabitDao,
    private val mapper: HabitTimeLogMapper
) : HabitTimeLogService {

    @Transactional
    override fun create(request: HabitTimeLogRequest, userId: Long): HabitTimeLogResponse {
        // Проверка: habit принадлежит userId
        val habit = habitDao.findById(request.habitId)
            .orElseThrow { throw EntityNotFoundException("Habit not found") }

        if (habit.userId != userId) {
            throw AccessDeniedException("You do not have permission to track time for this habit")
        }

        val timeLog = mapper.asEntity(request)
        logDao.save(timeLog)
        return mapper.asResponse(timeLog)
    }

    @Transactional(readOnly = true)
    override fun getById(id: Long, userId: Long): HabitTimeLogResponse {
        val log = logDao.findById(id)
            .orElseThrow { throw EntityNotFoundException("HabitTimeLog not found") }

        // Проверка принадлежности
        val habit = habitDao.findById(log.habitId)
            .orElseThrow { throw EntityNotFoundException("Habit not found") }

        if (habit.userId != userId) {
            throw AccessDeniedException("You do not have permission to access this time log")
        }

        return mapper.asResponse(log)
    }

    @Transactional
    override fun delete(id: Long, userId: Long) {
        val log = logDao.findById(id)
            .orElseThrow { throw EntityNotFoundException("HabitTimeLog not found") }

        val habit = habitDao.findById(log.habitId)
            .orElseThrow { throw EntityNotFoundException("Habit not found") }

        if (habit.userId != userId) {
            throw AccessDeniedException("You do not have permission to delete this time log")
        }

        logDao.delete(log)
    }

    @Transactional(readOnly = true)
    override fun list(userId: Long): List<HabitTimeLogResponse> {
        val habitIds = habitDao.findByUserId(userId).map { it.id }
        val logs = logDao.findByHabitIdIn(habitIds)
        return mapper.asListResponse(logs)
    }
}
