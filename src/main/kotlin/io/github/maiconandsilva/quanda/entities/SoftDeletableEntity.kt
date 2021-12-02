package io.github.maiconandsilva.quanda.entities

import com.fasterxml.jackson.annotation.JsonView
import io.github.maiconandsilva.quanda.utils.sec.Views
import io.github.maiconandsilva.quanda.utils.validation.Groups
import java.io.Serializable
import java.util.Date
import javax.persistence.*
import javax.validation.constraints.FutureOrPresent

@MappedSuperclass
abstract class SoftDeletableEntity<ID : Serializable?> : BaseEntity<ID>() {
    @JsonView(Views.Private::class)
    @FutureOrPresent(groups = [Groups.OnUpdate::class])
    @Temporal(TemporalType.TIMESTAMP)
    open var deletedDate: Date? = null

    fun softDelete() {
        deletedDate = Date()
    }
}
