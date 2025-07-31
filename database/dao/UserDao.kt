package backend.workshop.database.dao

import backend.workshop.database.entity.User

interface UserDao: CommonDao<User>{
    fun findEntityById(id: Long): User?
    fun existsByName(name: String): Boolean
    fun findByEmail(email: String): User?
}
