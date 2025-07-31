package backend.workshop.service.impl

import backend.workshop.database.dao.HabitDao
import backend.workshop.database.dao.HabitEntryDao
import backend.workshop.model.mapper.HabitEntryMapper
import backend.workshop.model.request.HabitEntryRequest
import backend.workshop.model.response.HabitEntryResponse
import backend.workshop.service.HabitEntryService
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.security.access.AccessDeniedException
import org.springframework.transaction.annotation.Transactional

@Service
class HabitEntryServiceImpl(
    private val entryDao: HabitEntryDao,
    private val habitDao: HabitDao,
    private val mapper: HabitEntryMapper
) : HabitEntryService {

    @Transactional
    override fun create(request: HabitEntryRequest, userId: Long): HabitEntryResponse {
        val habit = habitDao.findById(request.habitId)
            .orElseThrow { throw EntityNotFoundException("Habit not found") }

        if (habit.userId != userId) {
            throw AccessDeniedException("You do not have access to this habit")
        }

        val entry = mapper.asEntity(request)
        entryDao.save(entry)
        return mapper.asResponse(entry)
    }

    @Transactional(readOnly = true)
    override fun getById(id: Long, userId: Long): HabitEntryResponse {
        val entry = entryDao.findById(id)
            .orElseThrow { throw EntityNotFoundException("HabitEntry not found") }

        val habit = habitDao.findById(entry.habitId)
            .orElseThrow { throw EntityNotFoundException("Habit not found") }

        if (habit.userId != userId) {
            throw AccessDeniedException("You do not have access to this habit entry")
        }

        return mapper.asResponse(entry)
    }

    @Transactional
    override fun update(id: Long, request: HabitEntryRequest, userId: Long): HabitEntryResponse {
        val entry = entryDao.findById(id)
            .orElseThrow { throw EntityNotFoundException("HabitEntry not found") }

        val habit = habitDao.findById(entry.habitId)
            .orElseThrow { throw EntityNotFoundException("Habit not found") }

        if (habit.userId != userId) {
            throw AccessDeniedException("You do not have access to this habit entry")
        }

        val updated = mapper.update(entry, request)
        return mapper.asResponse(updated)
    }

    @Transactional
    override fun delete(id: Long, userId: Long) {
        val entry = entryDao.findById(id)
            .orElseThrow { throw EntityNotFoundException("HabitEntry not found") }

        val habit = habitDao.findById(entry.habitId)
            .orElseThrow { throw EntityNotFoundException("Habit not found") }

        if (habit.userId != userId) {
            throw AccessDeniedException("You do not have access to this habit entry")
        }

        entryDao.delete(entry)
    }

    @Transactional(readOnly = true)
    override fun list(userId: Long): List<HabitEntryResponse> {
        val habitIds = habitDao.findByUserId(userId).map { it.id }
        val entries = entryDao.findByHabitIdIn(habitIds)
        return mapper.asListResponse(entries)
    }
}