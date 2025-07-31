package backend.workshop.model.request

import jakarta.validation.constraints.NotBlank

class AuthenticationRequest (
    @field:NotBlank(message = "Email cannot be blank")
    val email: String,
    @field:NotBlank(message = "Password cannot be blank")
    val password: String
)