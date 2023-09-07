package io.github.maiconandsilva.quanda.controllers

import com.fasterxml.jackson.annotation.JsonView
import io.github.maiconandsilva.quanda.entities.BaseEntity
import io.github.maiconandsilva.quanda.exceptions.ResourceNotFoundException
import io.github.maiconandsilva.quanda.services.persistence.PersistenceService
import io.github.maiconandsilva.quanda.utils.sec.Views
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.io.Serializable

interface ResourceController<
        ID : Serializable?,
        Entity: BaseEntity<ID>,
        Repository: JpaRepository<Entity, ID>,
        Service: PersistenceService<ID, Entity, Repository>> {

    val service: Service

    @JsonView(Views.Public::class)
    @GetMapping("/{id}")
    fun get(@PathVariable id: ID): Entity? = service.get(id)

    @JsonView(Views.Public::class)
    @GetMapping("/all")
    fun getAll(pageable: Pageable): Page<Entity> = service.getAll(pageable)

    @JsonView(Views.Public::class)
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody entity: Entity): Entity {
        return service.create(entity)
    }

//    @JsonView(Views.Public::class)
//    @PostMapping("/{id}")
//    fun update(@PathVariable id: ID, @RequestBody entity: Entity) {
//        val savedEntity = foundOr404(service.get(id))
//        service.update(id, savedEntity, entity)
//    }

    @JsonView(Views.Public::class)
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: ID) = service.delete(foundOr404(service.get(id)))

    @Throws(ResourceNotFoundException::class)
    fun foundOr404(entity: Entity?): Entity
        = if (entity !== null) entity else throw ResourceNotFoundException()

}
