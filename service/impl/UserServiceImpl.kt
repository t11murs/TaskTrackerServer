package backend.workshop.service.impl

import backend.workshop.database.dao.UserDao
import backend.workshop.model.mapper.UserMapper
import backend.workshop.model.request.AuthenticationRequest
import backend.workshop.model.request.RegisterRequest
import backend.workshop.model.request.UserRequest
import backend.workshop.model.response.AuthenticationResponse
import backend.workshop.model.response.UserResponse
import backend.workshop.service.UserService
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val dao: UserDao,
    private val mapper: UserMapper,
) : UserService {

    override fun list(): List<UserResponse> {
        val users = dao.findAll()
        return mapper.asListResponse(users)
    }

    override fun getById(id: Long): UserResponse {
        val user = dao.findEntityById(id) ?: throw Exception("Not found")
        return mapper.asResponse(user)
    }

    @Transactional
    override fun create(request: UserRequest): UserResponse {
            if (dao.existsByName(request.name)) {
                throw IllegalArgumentException("Name is already taken")
            }

            val user = mapper.asEntity(request)

            dao.save(user)
            return mapper.asResponse(user)
    }

    @Transactional
    @Modifying
    override fun update(id: Long, request: UserRequest): UserResponse {
        val user = dao.findEntityById(id) ?: throw Exception("Not found")
        val updated = mapper.update(user, request)
        return mapper.asResponse(updated)
    }

    @Transactional
    @Modifying
    override fun delete(id: Long) {
        val user = dao.findEntityById(id) ?: throw Exception("Not found")
        dao.delete(user)
    }

}