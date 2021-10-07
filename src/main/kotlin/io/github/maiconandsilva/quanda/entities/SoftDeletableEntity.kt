package io.github.maiconandsilva.quanda.entities

import com.fasterxml.jackson.annotation.JsonView
import io.github.maiconandsilva.quanda.utils.sec.Views
import java.io.Serializable
import java.util.Date
import javax.persistence.*

@MappedSuperclass
abstract class SoftDeletableEntity<ID : Serializable?> : BaseEntity<ID>() {
    @JsonView(Views.Private::class)
    @Temporal(TemporalType.TIMESTAMP)
    open var deletedDate: Date? = null

    fun softDelete() {
        deletedDate = Date()
    }
}
