package io.github.maiconandsilva.quanda.entities

import com.fasterxml.jackson.annotation.JsonView
import io.github.maiconandsilva.quanda.utils.sec.Views
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(schema = "posts")
@Inheritance(strategy = InheritanceType.JOINED)
open class Post(
    @field:Size(min = 100, max = 1000)
    @Column(columnDefinition = "text", nullable = false)
    @JsonView(Views.Public::class)
    open var text: String,

    @JsonView(Views.Public::class)
    @ManyToOne(optional = false)
    open var author: User,

) : AuditableEntity<UUID?>()
