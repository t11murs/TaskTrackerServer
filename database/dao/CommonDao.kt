package backend.workshop.database.dao

import backend.workshop.database.entity.AbstractEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface CommonDao <T : AbstractEntity>: CrudRepository<T, Long> {
}