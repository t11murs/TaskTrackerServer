package backend.workshop.service

import backend.workshop.model.request.LoginRequest
import backend.workshop.model.request.RegisterRequest
import backend.workshop.model.response.UserResponse

interface AuthService{
    fun register(request: RegisterRequest): UserResponse

    fun login(request: LoginRequest): String
}