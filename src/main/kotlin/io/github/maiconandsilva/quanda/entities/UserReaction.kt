package io.github.maiconandsilva.quanda.entities

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(schema = "users")
data class UserReaction(
    @ManyToOne(optional = false)
    var user: User,

    @ManyToOne(optional = false)
    var post: Post,

    @ManyToOne(optional = false)
    var type: ReactionType,

) : BaseEntity<Long?>()
