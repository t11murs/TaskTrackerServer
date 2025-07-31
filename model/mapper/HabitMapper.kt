package backend.workshop.model.mapper

import backend.workshop.database.entity.Habit
import backend.workshop.model.request.HabitRequest
import backend.workshop.model.response.HabitResponse
import org.springframework.stereotype.Component

@Component
class HabitMapper {

    fun asEntity(request: HabitRequest, userId: Long) = Habit(
        name = request.name,
        userId = userId,
        description = request.description,
        goalType = request.goalType,
        goalCount = request.goalCount,
        remind = request.remind,
        remindTime = request.remindTime
    )

    fun asResponse(habit: Habit) = HabitResponse(
        id = habit.id,
        createdAt = habit.createdAt,
        name = habit.name,
        description = habit.description,
        userId = habit.userId,
        goalType = habit.goalType,
        goalCount = habit.goalCount,
        remind = habit.remind,
        remindTime = habit.remindTime
    )

    fun update(habit: Habit, request: HabitRequest): Habit {
        habit.name = request.name
        habit.description = request.description
        habit.goalType = request.goalType
        habit.goalCount = request.goalCount
        habit.remind = request.remind
        habit.remindTime = request.remindTime
        return habit
    }

    fun asListResponse(habits: Iterable<Habit>) = habits.map { asResponse(it) }
}