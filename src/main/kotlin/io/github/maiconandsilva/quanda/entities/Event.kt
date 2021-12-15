package io.github.maiconandsilva.quanda.entities

import io.github.maiconandsilva.quanda.consts.Schema
import javax.persistence.*

@Entity
@Table(schema = Schema.LOGGING)
@Inheritance(strategy = InheritanceType.JOINED)
open class Event(
    @Column(nullable = false, unique = true)
    open var name: String,

    @Column(columnDefinition = "text", nullable = false)
    open var description: String,
) : BaseEntity<Long>()
