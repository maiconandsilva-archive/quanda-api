package io.github.maiconandsilva.quanda.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(schema = "users")
data class ReactionType(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var reputation: Int,

) : BaseEntity<Long?>()
