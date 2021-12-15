package io.github.maiconandsilva.quanda.entities

import io.github.maiconandsilva.quanda.consts.Schema
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(schema = Schema.LOGGING)
data class EventLoggingPost(
    @ManyToOne(optional = false)
    override var event: Event,

    @ManyToOne(optional = false)
    override var initiator: User,

    @ManyToOne
    override var subject: User,

    @ManyToOne(optional = false)
    var post: Post,

) : EventLogging(event, initiator, subject)
