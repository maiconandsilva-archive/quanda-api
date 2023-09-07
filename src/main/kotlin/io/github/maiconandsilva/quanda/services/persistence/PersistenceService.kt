package io.github.maiconandsilva.quanda.services.persistence

import io.github.maiconandsilva.quanda.entities.BaseEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.validation.annotation.Validated
import java.io.Serializable
import javax.transaction.Transactional

@Validated
@Transactional
interface PersistenceService<ID: Serializable?, Entity: BaseEntity<in ID>,
                            Repository: JpaRepository<Entity, ID>> {
    val repository: Repository

    fun getAll(pageable: Pageable): Page<Entity> = repository.findAll(pageable)
    fun get(id: ID): Entity = repository.getById(id)
//    fun search(query: String): Page<Entity>
    fun create(entity: Entity): Entity = save(entity)
    fun update(id: ID, entityUpdate: Entity) {
//        val entity = repository.getById(id)
//        entity.merge(entityUpdate)
//        return save(entity)
    }
    fun delete(entity: Entity) = repository.delete(entity)
    fun save(entity: Entity): Entity = repository.save(entity)
}
