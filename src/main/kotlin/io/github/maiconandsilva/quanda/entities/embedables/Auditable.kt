package io.github.maiconandsilva.quanda.entities.embedables

import java.util.Date
import javax.persistence.*


@Embeddable
class Auditable: IAuditable {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    override lateinit var createdDate: Date

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    override lateinit var lastModifiedDate: Date

    @PrePersist
    fun persistCreatedDate() {
        createdDate = Date()
        lastModifiedDate = Date()
    }

    @PostUpdate
    fun updateLastModifiedDate() {
        lastModifiedDate = Date()
    }
}
