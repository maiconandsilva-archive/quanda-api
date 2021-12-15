package io.github.maiconandsilva.quanda.entities

import io.github.maiconandsilva.quanda.consts.Schema
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.Size

@Entity
@Table(schema = Schema.POST)
data class Question(
    @field:Size(min = 10, max = 200)
    @Column(length = 200, nullable = false)
    var title: String,

    override var text: String,

    @ManyToOne
    override var author: User,

    @field:Size(min = 1)
    @JoinTable(schema = Schema.POST)
    @ManyToMany(cascade = [CascadeType.ALL])
    var tags: MutableSet<Tag> = mutableSetOf(),

    @OneToOne
    var acceptedAnswer: Answer? = null,

) : Post(text, author) {
    @field:Min(0)
    var views: Int = 0
        private set
}
