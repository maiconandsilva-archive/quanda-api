package io.github.maiconandsilva.quanda.entities

import javax.persistence.*
import java.util.UUID
import javax.validation.constraints.Size
import com.fasterxml.jackson.annotation.JsonView
import io.github.maiconandsilva.quanda.utils.sec.Views

@Entity
@Table(schema = "posts")
data class Comment(
    @JsonView(Views.Public::class)
    @field:Size(min = 7, max = 400)
    @Column(columnDefinition = "text", nullable = false)
    var text: String,

    @JsonView(Views.Public::class)
    @ManyToOne(optional = false)
    var author: User,

    @ManyToOne(optional = false)
    var post: Post,

) : AuditableEntity<UUID>()
