package backend.workshop.model.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class UserRequest(
    @field:NotBlank
    val name: String,

    @field:NotBlank
    val email: String,

)