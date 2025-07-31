package backend.workshop.controller.impl

import backend.workshop.controller.SubtaskTimeLogController
import backend.workshop.model.request.SubtaskTimeLogRequest
import backend.workshop.model.response.SubtaskTimeLogResponse
import backend.workshop.service.JwtService
import backend.workshop.service.SubtaskTimeLogService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/subtask-time-log")
class SubtaskTimeLogControllerImpl(
    private val service: SubtaskTimeLogService,
    private val jwtService: JwtService
) {

    @PostMapping
    fun create(@RequestBody request: SubtaskTimeLogRequest, @RequestHeader("Authorization") token: String): SubtaskTimeLogResponse {
        val userId = jwtService.extractUserId(token)
        return service.create(request, userId)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long, @RequestHeader("Authorization") token: String): SubtaskTimeLogResponse {
        val userId = jwtService.extractUserId(token)
        return service.getById(id, userId)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long, @RequestHeader("Authorization") token: String) {
        val userId = jwtService.extractUserId(token)
        service.delete(id, userId)
    }

    @GetMapping
    fun list(@RequestHeader("Authorization") token: String): List<SubtaskTimeLogResponse> {
        val userId = jwtService.extractUserId(token)
        return service.list(userId)
    }
}
