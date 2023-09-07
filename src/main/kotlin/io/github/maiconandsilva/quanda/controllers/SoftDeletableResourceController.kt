package io.github.maiconandsilva.quanda.controllers

import io.github.maiconandsilva.quanda.entities.BaseEntity
import io.github.maiconandsilva.quanda.entities.SoftDeletableEntity
import io.github.maiconandsilva.quanda.repositories.UserRepository
import io.github.maiconandsilva.quanda.services.persistence.PersistenceService
import io.github.maiconandsilva.quanda.exceptions.ResourceNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.beans.factory.annotation.Autowired
import java.io.Serializable

interface SoftDeletableResourceController<
    ID : Serializable,
    Entity: SoftDeletableEntity<ID>,
    Repository: JpaRepository<Entity, ID>,
    Service: PersistenceService<ID, Entity, Repository>
> : ResourceController<ID, Entity, Repository, Service> {

    override fun delete(@PathVariable id: ID) {
        val entity = service.repository.getById(id)

        entity.softDelete()
        service.save(entity)
    }
}
