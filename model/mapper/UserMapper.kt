package backend.workshop.model.mapper

import backend.workshop.database.entity.User
import backend.workshop.model.request.RegisterRequest
import backend.workshop.model.request.UserRequest
import backend.workshop.model.response.UserResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserMapper(
    private val passwordEncoder: PasswordEncoder
) {

    fun asEntity(request: RegisterRequest) = User(
        name = request.name,
        email = request.email,
        password = passwordEncoder.encode(request.password),
    )

    fun asEntity(request: UserRequest) = User(
        name = request.name,
        email = request.email,
        password = ""
    )

    fun asResponse(user: User) = UserResponse(
        name = user.name,
        email = user.email,
        id = user.id,
        createdAt = user.createdAt,
    )

    fun update(user: User, request: UserRequest): User {
        user.name = request.name
        user.email = request.email
        return user
    }

    fun asListResponse(users: Iterable<User>) = users.map { asResponse(it) }
}