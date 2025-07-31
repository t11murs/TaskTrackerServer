package backend.workshop.controller.impl

import backend.workshop.controller.HealthController
import backend.workshop.model.message.HealthMessage
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthControllerImpl : HealthController {
    @GetMapping("/health")
    override fun health(): HealthMessage = HealthMessage()
}