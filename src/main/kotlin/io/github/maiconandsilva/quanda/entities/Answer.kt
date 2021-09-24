package io.github.maiconandsilva.quanda.entities

import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
data class Answer(
    override val text: String,

    @ManyToOne(optional = false)
    override val author: User,

    @ManyToOne(optional = false)
    val question: Question,
) : Post(text, author)
