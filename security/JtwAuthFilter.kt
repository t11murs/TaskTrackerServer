package backend.workshop.security

import backend.workshop.service.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtService: JwtService
) : OncePerRequestFilter() {

    companion object {
        private val log = LoggerFactory.getLogger(JwtAuthFilter::class.java)
        private const val BEARER = "Bearer "
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val header = request.getHeader("Authorization")
        log.info("JwtAuthFilter: got header='{}'", header)

        if (header.isNullOrBlank() || !header.startsWith(BEARER)) {
            chain.doFilter(request, response)
            return
        }

        val token = header.removePrefix(BEARER).trim()
        if (!jwtService.validateToken(token)) {
            chain.doFilter(request, response)
            return
        }

        // берём из subject тот самый userId
        val userId = jwtService.parseToken(token).subject
        val auth = UsernamePasswordAuthenticationToken(userId, null, emptyList())
        SecurityContextHolder.getContext().authentication = auth

        chain.doFilter(request, response)
    }
}
