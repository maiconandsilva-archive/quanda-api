package io.github.maiconandsilva.quanda.entities

import io.github.maiconandsilva.quanda.consts.Schema
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(schema = Schema.POST)
data class Answer(
    override var text: String,

    @ManyToOne
    override var author: User,

    @ManyToOne(optional = false)
    var question: Question,

) : Post(text, author)
