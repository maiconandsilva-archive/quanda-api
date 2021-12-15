package io.github.maiconandsilva.quanda.entities

import io.github.maiconandsilva.quanda.consts.Schema
import io.github.maiconandsilva.quanda.utils.validation.Groups
import java.util.*
import javax.persistence.*
import javax.validation.constraints.FutureOrPresent

@Entity
@Table(schema = Schema.LOGGING)
@Inheritance(strategy = InheritanceType.JOINED)
open class EventLogging(
    @ManyToOne(optional = false)
    open var event: Event,

    @ManyToOne(optional = false)
    open var initiator: User,

    @ManyToOne
    open var subject: User,
) : BaseEntity<Long>() {
    @FutureOrPresent(groups = [Groups.OnUpdate::class])
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    open lateinit var timestamp: Date

    @PrePersist
    private fun persistCreatedDate() {
        timestamp = Date()
    }
}
