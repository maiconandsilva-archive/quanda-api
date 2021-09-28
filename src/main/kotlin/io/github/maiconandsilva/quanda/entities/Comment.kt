package io.github.maiconandsilva.quanda.entities

import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(schema = "posts")
@DiscriminatorValue("C")
data class Comment(
    @Size(min = 7, max = 400)
    override val text: String,

    @ManyToOne(optional = false)
    val post: Post,

    @ManyToOne(optional = false)
    override val author: User,
) : PostContent(text, author)
