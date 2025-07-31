package backend.workshop.service.impl

import backend.workshop.database.dao.HabitDao
import backend.workshop.model.mapper.HabitMapper
import backend.workshop.model.request.HabitRequest
import backend.workshop.model.response.HabitResponse
import backend.workshop.service.HabitService

import jakarta.persistence.EntityNotFoundException
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HabitServiceImpl(
    private val habitDao: HabitDao,
    private val mapper: HabitMapper
) : HabitService {

    @Transactional
    override fun create(request: HabitRequest, userId: Long): HabitResponse {
        val habit = mapper.asEntity(request, userId)
        habitDao.save(habit)
        return mapper.asResponse(habit)
    }

    @Transactional(readOnly = true)
    override fun getById(id: Long, userId: Long): HabitResponse {
        val habit = habitDao.findById(id)
            .orElseThrow { throw EntityNotFoundException("Habit not found") }

        if (habit.userId != userId) {
            throw AccessDeniedException("You do not have access to this habit")
        }

        return mapper.asResponse(habit)
    }

    @Transactional
    override fun update(id: Long, request: HabitRequest, userId: Long): HabitResponse {
        val habit = habitDao.findById(id)
            .orElseThrow { throw EntityNotFoundException("Habit not found") }

        if (habit.userId != userId) {
            throw AccessDeniedException("You do not have access to this habit")
        }

        val updated = mapper.update(habit, request)
        return mapper.asResponse(updated)
    }

    @Transactional
    override fun delete(id: Long, userId: Long) {
        val habit = habitDao.findById(id)
            .orElseThrow { throw EntityNotFoundException("Habit not found") }

        if (habit.userId != userId) {
            throw AccessDeniedException("You do not have access to this habit")
        }

        habitDao.delete(habit)
    }

    @Transactional(readOnly = true)
    override fun list(userId: Long): List<HabitResponse> {
        return mapper.asListResponse(habitDao.findByUserId(userId))
    }
}
