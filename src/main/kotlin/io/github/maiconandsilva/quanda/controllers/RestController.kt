package io.github.maiconandsilva.quanda.controllers

import io.github.maiconandsilva.quanda.entities.BaseEntity
import org.springframework.web.bind.annotation.RequestBody

interface RestController {
    fun get() : BaseEntity
    fun getAll() : Set<BaseEntity>
    fun update(@RequestBody entity: BaseEntity)
    fun delete(@RequestBody entity: BaseEntity)
    fun deleteAll(@RequestBody entity: Set<BaseEntity>)
}
