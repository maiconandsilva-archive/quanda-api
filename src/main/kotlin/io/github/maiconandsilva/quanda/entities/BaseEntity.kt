package io.github.maiconandsilva.quanda.entities

import java.io.Serializable
import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: UUID? = null
) : Serializable
