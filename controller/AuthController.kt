package backend.workshop.controller

import backend.workshop.model.request.LoginRequest
import backend.workshop.model.request.RegisterRequest
import backend.workshop.model.response.UserResponse

interface AuthController {
    fun register(request: RegisterRequest): UserResponse

    fun login(request: LoginRequest): String
}