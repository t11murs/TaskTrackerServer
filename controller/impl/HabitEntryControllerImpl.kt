package backend.workshop.controller.impl

import backend.workshop.controller.HabitEntryController
import backend.workshop.model.request.HabitEntryRequest
import backend.workshop.model.response.HabitEntryResponse
import backend.workshop.service.HabitEntryService
import backend.workshop.service.JwtService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/habit-entry")
class HabitEntryControllerImpl(
    private val service: HabitEntryService,
    private val jwtService: JwtService
): HabitEntryController {

    @PostMapping
    override fun create(@RequestBody request: HabitEntryRequest, @RequestHeader("Authorization") token: String): HabitEntryResponse {
        val userId = jwtService.extractUserId(token)
        return service.create(request, userId)
    }

    @GetMapping("/{id}")
    override fun getById(@PathVariable id: Long, @RequestHeader("Authorization") token: String): HabitEntryResponse {
        val userId = jwtService.extractUserId(token)
        return service.getById(id, userId)
    }

    @PutMapping("/{id}")
    override fun update(@PathVariable id: Long, @RequestBody request: HabitEntryRequest, @RequestHeader("Authorization") token: String): HabitEntryResponse {
        val userId = jwtService.extractUserId(token)
        return service.update(id, request, userId)
    }

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: Long, @RequestHeader("Authorization") token: String) {
        val userId = jwtService.extractUserId(token)
        service.delete(id, userId)
    }

    @GetMapping
    override fun list(@RequestHeader("Authorization") token: String): List<HabitEntryResponse> {
        val userId = jwtService.extractUserId(token)
        return service.list(userId)
    }
}
