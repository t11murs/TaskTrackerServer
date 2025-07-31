package backend.workshop.service.impl

import backend.workshop.database.entity.User
import backend.workshop.service.JwtService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import java.util.*
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service

@Service
class JwtServiceImpl(
    @Value("\${jwt.secret}") private val secret: String
) : JwtService {

    override fun generateToken(user: User): String {
        val now = Date()

        return Jwts.builder()
            .setSubject(user.id.toString())
            .claim("email", user.email)
            .setIssuedAt(now)
            // удалили setExpiration — токен не истекает
            .signWith(SignatureAlgorithm.HS256, secret.toByteArray())
            .compact()
    }

    override fun parseToken(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(secret.toByteArray())
            .parseClaimsJws(token)
            .body
    }

    override fun validateToken(token: String): Boolean =
        try {
            // если токена нет exp, то parseClaimsJws не бросит ExpiredJwtException
            parseToken(token)
            true
        } catch (e: Exception) {
            false
        }

    override fun extractUserId(authHeader: String): Long {
        val token = authHeader.removePrefix("Bearer ").trim()
        val claims = parseToken(token)
        return claims.subject.toLong()
    }

    fun extractEmail(authHeader: String): String {
        val token = authHeader.removePrefix("Bearer ").trim()
        val claims = parseToken(token)
        return claims["email"] as String
    }
}