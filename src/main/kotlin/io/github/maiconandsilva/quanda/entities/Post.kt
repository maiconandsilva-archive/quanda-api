package io.github.maiconandsilva.quanda.entities

import java.util.*
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(schema = "posts")
@Inheritance(strategy = InheritanceType.JOINED)
open class Post(
    @Size(min = 100, max = 1000)
    @Column(columnDefinition = "text", nullable = false)
    open var text: String,

    @ManyToOne(optional = false)
    open var author: User,

) : AuditableEntity<UUID?>()
