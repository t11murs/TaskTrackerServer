package backend.workshop.model.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class RegisterRequest(
    @field:NotBlank(message = "Имя не может быть пустым")
    val name: String,
    @field:NotBlank(message = "Электронная почта не может быть пустой")
    val email: String,
    @field:Size(min = 8, message = "Пароль должен быть не короче 8 символов")
    val password: String
)