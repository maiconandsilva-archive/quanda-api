package io.github.maiconandsilva.quanda.entities

import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
data class UserReaction(
    @ManyToOne(optional = false)
    val user: User,

    @ManyToOne(optional = false)
    val post: Post,

    @ManyToOne(optional = false)
    val type: ReactionType,
) : BaseEntity()
