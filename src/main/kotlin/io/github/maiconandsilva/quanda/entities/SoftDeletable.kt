package io.github.maiconandsilva.quanda.entities

import java.util.*
import javax.persistence.Embeddable
import javax.persistence.Temporal
import javax.persistence.TemporalType

interface SoftDeletable {
    var deletedDate: Date?

    fun softDelete() {
        deletedDate = Date()
    }
}
