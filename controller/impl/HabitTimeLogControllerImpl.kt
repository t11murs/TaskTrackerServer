package backend.workshop.controller.impl

import backend.workshop.controller.HabitTimeLogController
import backend.workshop.model.request.HabitTimeLogRequest
import backend.workshop.model.response.HabitTimeLogResponse
import backend.workshop.service.HabitTimeLogService
import backend.workshop.service.JwtService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/habit-time-log")
class HabitTimeLogControllerImpl(
    private val service: HabitTimeLogService,
    private val jwtService: JwtService
): HabitTimeLogController {

    @PostMapping
    override fun create(@RequestBody request: HabitTimeLogRequest, @RequestHeader("Authorization") token: String): HabitTimeLogResponse {
        val userId = jwtService.extractUserId(token)
        return service.create(request, userId)
    }

    @GetMapping("/{id}")
    override fun getById(@PathVariable id: Long, @RequestHeader("Authorization") token: String): HabitTimeLogResponse {
        val userId = jwtService.extractUserId(token)
        return service.getById(id, userId)
    }

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: Long, @RequestHeader("Authorization") token: String) {
        val userId = jwtService.extractUserId(token)
        service.delete(id, userId)
    }

    @GetMapping
    override fun list(@RequestHeader("Authorization") token: String): List<HabitTimeLogResponse> {
        val userId = jwtService.extractUserId(token)
        return service.list(userId)
    }
}
