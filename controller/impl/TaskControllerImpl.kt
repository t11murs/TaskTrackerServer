package backend.workshop.controller.impl

import backend.workshop.controller.TaskController
import backend.workshop.model.message.DeletedMessage
import backend.workshop.model.request.TaskRequest
import backend.workshop.model.response.TaskResponse
import backend.workshop.service.TaskService
import backend.workshop.service.JwtService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/task")
class TaskControllerImpl(
    private val service: TaskService,
    private val jwtService: JwtService
): TaskController {

    @GetMapping
    override fun list(@RequestHeader("Authorization") token: String): List<TaskResponse> {
        val userId = jwtService.extractUserId(token)
        return service.listTasksForUser(userId)
    }

    @GetMapping("/{id}")
    override fun getById(@PathVariable id: Long, @RequestHeader("Authorization") token: String): TaskResponse {
        val userId = jwtService.extractUserId(token)
        return service.getByIdForUser(id, userId)
    }

    @PostMapping
    override fun create(@RequestBody @Valid request: TaskRequest, @RequestHeader("Authorization") token: String): TaskResponse {
        val userId = jwtService.extractUserId(token)
        return service.createForUser(request, userId)
    }

    @PutMapping("/{id}")
    override fun update(
        @PathVariable id: Long,
        @RequestBody @Valid request: TaskRequest,
        @RequestHeader("Authorization") token: String
    ): TaskResponse {
        val userId = jwtService.extractUserId(token)
        return service.updateForUser(id, request, userId)
    }

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: Long, @RequestHeader("Authorization") token: String): DeletedMessage {
        val userId = jwtService.extractUserId(token)
        service.deleteByIdForUser(id, userId)
        return DeletedMessage()
    }
}
