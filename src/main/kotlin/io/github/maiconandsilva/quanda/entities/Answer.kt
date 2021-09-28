package io.github.maiconandsilva.quanda.entities

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(schema = "posts")
@DiscriminatorValue("A")
data class Answer(
    override val text: String,

    @ManyToOne
    override val author: User,

    @ManyToOne(optional = false)
    val question: Question,
) : PostContent(text, author)
