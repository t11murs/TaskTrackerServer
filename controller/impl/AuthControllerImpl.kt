package backend.workshop.controller.impl

import backend.workshop.controller.AuthController
import backend.workshop.database.dao.UserDao
import backend.workshop.model.request.LoginRequest
import backend.workshop.model.request.RegisterRequest
import backend.workshop.model.response.UserResponse
import backend.workshop.service.AuthService
import backend.workshop.service.impl.JwtServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class AuthControllerImpl(
    private val authService: AuthService,
    private val passwordEncoder: PasswordEncoder,
    private val dao: UserDao,
    private val jwtService: JwtServiceImpl,
) : AuthController {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    override fun register(@RequestBody request: RegisterRequest): UserResponse =
        authService.register(request)

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    override fun login(@RequestBody request: LoginRequest): String {
        val user = dao.findByEmail(request.email)
            ?: throw Exception("User not found")
        if (!passwordEncoder.matches(request.password, user.password))
            throw Exception("Invalid credentials")
        return jwtService.generateToken(user)
    }

}