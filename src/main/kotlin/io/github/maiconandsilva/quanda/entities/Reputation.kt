package io.github.maiconandsilva.quanda.entities

import io.github.maiconandsilva.quanda.consts.Schema
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(schema = Schema.USER)
data class Reputation(
    override var name: String,

    override var description: String,

    @Column(nullable = false)
    var points: Int,

) : Event(name, description)
