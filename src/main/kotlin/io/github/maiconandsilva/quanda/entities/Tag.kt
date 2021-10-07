package io.github.maiconandsilva.quanda.entities

import io.github.maiconandsilva.quanda.consts.Patterns
import javax.persistence.*
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
@Table(schema = "posts")
data class Tag(
    @field:Size(min = 1, max = 24)
    @field:Pattern(regexp = Patterns.NO_ESPECIAL_CHARS)
    @Column(length = 24, nullable = false)
    var name: String,

    @field:Size(min = 30, max = 100)
    @Column(length = 100, nullable = false)
    var description: String,

    @ManyToMany(mappedBy = "tags")
    var questions: MutableSet<Question> = mutableSetOf(),

) : BaseEntity<Long?>()
