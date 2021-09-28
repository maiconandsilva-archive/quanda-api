package io.github.maiconandsilva.quanda.entities

import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.Size

@Entity
@Table(schema = "posts")
@DiscriminatorValue("Q")
data class Question(
    @Size(min = 10, max = 200)
    @Column(length = 200, nullable = false)
    var title: String,

    override val text: String,

    @ManyToOne
    override val author: User,

    @Size(min = 1)
    @JoinTable(schema = "posts")
    @ManyToMany(cascade = [CascadeType.ALL])
    val tags: MutableSet<Tag> = mutableSetOf(),

    @Min(0)
    private var views: Int = 0,
) : PostContent(text, author) {

    fun increaseViews() {
        views++
    }
}
