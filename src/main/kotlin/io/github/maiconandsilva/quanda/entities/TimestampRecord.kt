package io.github.maiconandsilva.quanda.entities

import java.util.*
import javax.persistence.Embeddable
import javax.persistence.PostUpdate
import javax.persistence.PrePersist
import javax.validation.constraints.PastOrPresent

@Embeddable
class TimestampRecord {
    @PastOrPresent
    lateinit var createdAt: Date

    @PastOrPresent
    lateinit var updatedAt: Date

    @PrePersist
    fun setTimestampCreateAt() {
        createdAt = Date()
    }

    @PostUpdate
    fun setTimestampUpdateAt() {
        updatedAt = Date()
    }
}

@Embeddable
class DeleteTimestampRecord {
    @PastOrPresent
    lateinit var deletedAt: Date

//    fun setTimestampCreateAt() {
//        deletedAt = Date()
//    }
}
