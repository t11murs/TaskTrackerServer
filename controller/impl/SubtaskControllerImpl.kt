package backend.workshop.controller.impl

import backend.workshop.controller.HabitController
import backend.workshop.model.request.HabitRequest
import backend.workshop.model.request.SubtaskRequest
import backend.workshop.model.response.HabitResponse
import backend.workshop.model.response.SubtaskResponse
import backend.workshop.service.HabitService
import backend.workshop.service.JwtService
import backend.workshop.service.SubtaskService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/subtask")
class SubtaskControllerImpl(
    private val service: SubtaskService,
    private val jwtService: JwtService
) {

    @PostMapping
    fun create(@RequestBody request: SubtaskRequest, @RequestHeader("Authorization") token: String): SubtaskResponse {
        val userId = jwtService.extractUserId(token)
        return service.create(request, userId)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long, @RequestHeader("Authorization") token: String): SubtaskResponse {
        val userId = jwtService.extractUserId(token)
        return service.getById(id, userId)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: SubtaskRequest, @RequestHeader("Authorization") token: String): SubtaskResponse {
        val userId = jwtService.extractUserId(token)
        return service.update(id, request, userId)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long, @RequestHeader("Authorization") token: String) {
        val userId = jwtService.extractUserId(token)
        service.delete(id, userId)
    }

    @GetMapping
    fun list(@RequestHeader("Authorization") token: String): List<SubtaskResponse> {
        val userId = jwtService.extractUserId(token)
        return service.list(userId)
    }
}
