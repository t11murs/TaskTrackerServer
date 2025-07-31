package backend.workshop.model.message

import backend.workshop.model.response.ApiResponse
import org.springframework.http.HttpStatus

abstract class AbstractApiMessage: ApiResponse {
    override val status: HttpStatus = HttpStatus.OK
}