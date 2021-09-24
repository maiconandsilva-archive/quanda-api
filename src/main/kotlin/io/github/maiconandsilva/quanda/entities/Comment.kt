package io.github.maiconandsilva.quanda.entities

import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.validation.constraints.Size

@Entity
data class Comment(
    @Size(min = 7, max = 400)
    override val text: String,

    @ManyToOne(optional = false)
    val post: Post,

    @ManyToOne(optional = false)
    override val author: User,
) : Post(text, author)
