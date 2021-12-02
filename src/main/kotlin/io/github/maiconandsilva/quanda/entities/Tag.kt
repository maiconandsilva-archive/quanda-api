package io.github.maiconandsilva.quanda.entities

import com.fasterxml.jackson.annotation.JsonView
import io.github.maiconandsilva.quanda.consts.Patterns
import io.github.maiconandsilva.quanda.utils.sec.Views
import javax.persistence.*
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
@Table(schema = "posts")
data class Tag(
    @JsonView(Views.Public::class)
    @field:Size(min = 1, max = 100)
    @field:Pattern(regexp = Patterns.NO_ESPECIAL_CHARS)
    @Column(length = 24, unique = true, nullable = false)
    var name: String,

    @JsonView(Views.Public::class)
    @field:Size(min = 10, max = 1000)
    @Column(length = 100, nullable = false)
    var description: String,

    @JsonView(Views.Tag::class)
    @ManyToMany(mappedBy = "tags")
    var questions: MutableSet<Question> = mutableSetOf(),

) : BaseEntity<Long>()
