package io.github.maiconandsilva.quanda.entities.embedables

import java.util.*
import javax.persistence.Embeddable
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Embeddable
class SoftDeletable: ISoftDeletable {
    @Temporal(TemporalType.TIMESTAMP)
    override var deletedDate: Date? = null

    override fun softDelete() {
        deletedDate = Date()
    }
}
