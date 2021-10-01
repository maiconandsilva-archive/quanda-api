package io.github.maiconandsilva.quanda.entities

import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(schema = "posts")
@DiscriminatorValue("C")
data class Comment(
    @Size(min = 7, max = 400)
    override var text: String,

    @ManyToOne(optional = false)
    var post: Post,

    @ManyToOne(optional = false)
    override var author: User,

) : PostContent(text, author)
