package backend.workshop.service.impl

import backend.workshop.database.dao.UserDao
import backend.workshop.model.mapper.UserMapper
import backend.workshop.model.request.LoginRequest
import backend.workshop.model.request.RegisterRequest
import backend.workshop.model.response.UserResponse
import backend.workshop.service.AuthService
import backend.workshop.service.JwtService
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class AuthServiceImpl(
    private val dao: UserDao,
    private val mapper: UserMapper,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) : AuthService {

    @Transactional
    override fun register(@RequestBody request: RegisterRequest): UserResponse {
        if (dao.existsByName(request.name)) {
            throw IllegalArgumentException("Name is already taken")
        }

        val user = mapper.asEntity(request).apply {
            password = passwordEncoder.encode(request.password)
        }

        dao.save(user)
        return mapper.asResponse(user)
    }

    override fun login(@RequestBody request: LoginRequest): String {
        val user = dao.findByEmail(request.email)
            ?: throw Exception("User not found")

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw Exception("Invalid credentials")
        }

        return jwtService.generateToken(user)
    }
}