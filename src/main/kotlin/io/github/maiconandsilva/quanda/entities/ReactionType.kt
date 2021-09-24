package io.github.maiconandsilva.quanda.entities

import javax.persistence.Entity

@Entity
data class ReactionType(
    val name: String,
    val reputation: Int,
) : BaseEntity()
