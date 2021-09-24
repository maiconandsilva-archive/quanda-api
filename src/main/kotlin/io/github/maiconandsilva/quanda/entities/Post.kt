package io.github.maiconandsilva.quanda.entities

import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
open class Post(
    @Size(min = 100, max = 1000)
    @Column(length = 1000, nullable = false)
    open val text: String,

    @ManyToOne(optional = false)
    open val author: User,
) : BaseEntity() {

    @Embedded
    private lateinit var timestampRecord: TimestampRecord

    @Embedded
    private var deleteTimestampRecord: DeleteTimestampRecord? = null
}
