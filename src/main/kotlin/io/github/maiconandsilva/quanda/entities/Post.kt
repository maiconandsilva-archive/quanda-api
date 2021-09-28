package io.github.maiconandsilva.quanda.entities

import io.github.maiconandsilva.quanda.entities.embedables.*
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(schema = "posts")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
open class Post : BaseEntity<UUID?>()

@MappedSuperclass
abstract class PostContent(
    @Size(min = 100, max = 1000)
    @Column(columnDefinition = "text", nullable = false)
    open val text: String,

    @ManyToOne(optional = false)
    open val author: User,

    @Embedded
    private val auditable: Auditable = Auditable(),

    @Embedded
    private val softDeletable: SoftDeletable = SoftDeletable(),
) : Post(), IAuditable by auditable, ISoftDeletable by softDeletable
