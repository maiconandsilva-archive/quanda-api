package io.github.maiconandsilva.quanda.entities

import java.io.Serializable
import java.util.Date
import javax.persistence.*

@MappedSuperclass
abstract class SoftDeletableEntity<ID : Serializable?> (
    @Temporal(TemporalType.TIMESTAMP)
    open var deletedDate: Date? = null,

) : BaseEntity<ID>() {
    fun softDelete() {
        deletedDate = Date()
    }
}
