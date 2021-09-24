package io.github.maiconandsilva.quanda.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.validation.constraints.Max
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
data class Tag(
    @Max(16)
    @NotEmpty
    @Pattern(regexp = Patterns.NO_ESPECIAL_CHARS)
    @Column(length = 16, nullable = false)
    val name: String,

    @Size(min = 30, max = 100)
    @Column(length = 100)
    val description: String,

    @ManyToMany(mappedBy = "tags")
    val questions: MutableSet<Question> = mutableSetOf(),
) : BaseEntity()
