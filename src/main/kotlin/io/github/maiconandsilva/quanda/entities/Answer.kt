package io.github.maiconandsilva.quanda.entities

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(schema = "posts")
@DiscriminatorValue("A")
data class Answer(
    override var text: String,

    @ManyToOne
    override var author: User,

    @ManyToOne(optional = false)
    var question: Question,

) : Post(text, author)
