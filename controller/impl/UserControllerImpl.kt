package backend.workshop.controller.impl

import backend.workshop.controller.UserController
import backend.workshop.model.message.DeletedMessage
import backend.workshop.model.request.RegisterRequest
import backend.workshop.model.request.UserRequest
import backend.workshop.model.response.UserResponse
import backend.workshop.service.JwtService
import backend.workshop.service.UserService
import org.hibernate.annotations.NotFound
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/user")
class UserControllerImpl(
    private val service: UserService,
) : UserController {
    @GetMapping
    override fun list() = service.list()

    @GetMapping("/{id}")
    override fun getByID(@PathVariable id: Long) = service.getById(id)

    @PostMapping
    override fun create(@RequestBody request: UserRequest) = service.create(request)

    @PutMapping("/{id}")
    override fun update(@PathVariable id: Long, @RequestBody request: UserRequest) = service.update(id, request)

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: Long): DeletedMessage {
        service.delete(id)
        return DeletedMessage()
    }

    @GetMapping("/me")
    fun me(principal: Principal): UserResponse {
        val userId = principal.name.toLong()
        return service.getById(userId)
    }
}