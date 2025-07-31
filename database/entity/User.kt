package backend.workshop.database.entity

import jakarta.persistence.*

@Entity
@Table(name = "user")
class User(
    @Column(nullable = false, length = 250)
    var name: String,

    @Column(nullable = false, length = 125)
    var email: String,

    @Column(nullable = false)
    var password: String,

) : AbstractEntity()
