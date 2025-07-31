package backend.workshop.controller

import backend.workshop.model.message.HealthMessage

interface HealthController {
    fun health(): HealthMessage
}