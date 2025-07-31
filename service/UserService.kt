package backend.workshop.service

import backend.workshop.model.request.AuthenticationRequest
import backend.workshop.model.request.RegisterRequest
import backend.workshop.model.request.UserRequest
import backend.workshop.model.response.AuthenticationResponse
import backend.workshop.model.response.UserResponse

interface UserService {
    fun list(): List<UserResponse>
    fun getById(id: Long): UserResponse
    fun create(request: UserRequest): UserResponse
    fun update(id: Long, request: UserRequest): UserResponse
    fun delete(id: Long)
}