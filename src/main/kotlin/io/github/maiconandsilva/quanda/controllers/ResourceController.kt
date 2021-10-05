package io.github.maiconandsilva.quanda.controllers

import io.github.maiconandsilva.quanda.entities.BaseEntity
import io.github.maiconandsilva.quanda.repositories.UserRepository
import io.github.maiconandsilva.quanda.services.persistence.PersistenceService
import io.github.maiconandsilva.quanda.exceptions.ResourceNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.io.Serializable

interface Transferable<T> {
    fun merge(t: Any) : T
}

interface ResourceController<
    ID : Serializable,
    Entity: BaseEntity<ID>,
    Repository: JpaRepository<Entity, ID>,
    Service: PersistenceService<ID, Entity, Repository>
> {
    val service: Service

    @GetMapping("/{id}")
    fun get(@PathVariable id: ID) : Entity? {
        return service.repository.getById(id)
    }

    @GetMapping("/")
    fun getAll() : Iterable<Entity> = service.repository.findAll()

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody entity: Entity) : Entity {
        return service.create(entity)
    }

    @PostMapping("/{id}")
    fun update(@PathVariable id: ID, @RequestBody entity: Entity) {
        val savedEntity = service.repository.getById(id)
        if (savedEntity is Transferable<*>) {
            savedEntity.merge(entity)
        }
        service.save(savedEntity)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: ID) {
        val entity: Entity? = service.repository.getById(id)

        if (entity === null) {
            throw ResourceNotFoundException()
        }
        service.repository.delete(entity)
    }
}
