package backend.workshop.service

import backend.workshop.database.entity.User
import io.jsonwebtoken.Claims

interface JwtService {
    fun generateToken(user: User): String
    fun parseToken(token: String): Claims
    fun validateToken(token: String): Boolean
    fun extractUserId(authHeader: String): Long
}