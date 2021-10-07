package io.github.maiconandsilva.quanda.entities

import com.fasterxml.jackson.annotation.JsonView
import io.github.maiconandsilva.quanda.utils.sec.Views
import java.io.Serializable
import java.util.Date
import javax.persistence.*


@MappedSuperclass
abstract class AuditableEntity<ID : Serializable?> : SoftDeletableEntity<ID>() {
    @JsonView(Views.Public::class)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    open lateinit var createdDate: Date

    @JsonView(Views.Public::class)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    open lateinit var lastModifiedDate: Date

    @PrePersist
    private fun persistCreatedDate() {
        createdDate = Date()
        lastModifiedDate = Date()
    }

    @PostUpdate
    private fun updateLastModifiedDate() {
        lastModifiedDate = Date()
    }
}
