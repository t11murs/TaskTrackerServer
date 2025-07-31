package backend.workshop.model.message

import backend.workshop.database.entity.AbstractEntity

class HealthMessage: AbstractApiMessage() {
    override val message: String = "Healthy"
}