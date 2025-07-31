package backend.workshop.controller

import backend.workshop.model.message.DeletedMessage
import backend.workshop.model.request.RegisterRequest
import backend.workshop.model.request.UserRequest
import backend.workshop.model.response.UserResponse

interface UserController {
    fun list(): List<UserResponse>
    fun getByID(id: Long): UserResponse
    fun create(request: UserRequest): UserResponse
    fun update(id: Long, request: UserRequest): UserResponse
    fun delete(id: Long): DeletedMessage
}