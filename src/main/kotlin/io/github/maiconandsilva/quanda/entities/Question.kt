package io.github.maiconandsilva.quanda.entities

import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.validation.constraints.Min
import javax.validation.constraints.Size

@Entity
data class Question(
    @Size(min = 10, max = 200)
    var title: String,

    override val text: String,

    @ManyToOne
    override val author: User,

    @ManyToMany
    @Size(min = 1)
    val tags: MutableSet<Tag> = mutableSetOf(),

    @Min(0)
    private var views: Int = 0,
) : Post(text, author) {
    fun increaseViews() {
        views++
    }
}
