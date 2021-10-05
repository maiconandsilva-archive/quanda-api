package io.github.maiconandsilva.quanda.services.persistence

import io.github.maiconandsilva.quanda.entities.BaseEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.io.Serializable

interface PersistenceService<ID: Serializable, Entity: BaseEntity<in ID>,
                            Repository: JpaRepository<Entity, ID>> {
    val repository: Repository

    fun create(entity: Entity) : Entity {
        return save(entity)
    }

    fun update(entity: Entity) : Entity {
        return save(entity)
    }

    fun delete(entity: Entity) {
        repository.delete(entity)
    }

    fun save(entity: Entity) : Entity {
        return repository.save(entity)
    }
}
