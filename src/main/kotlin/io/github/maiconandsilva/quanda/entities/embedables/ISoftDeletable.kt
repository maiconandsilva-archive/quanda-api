package io.github.maiconandsilva.quanda.entities.embedables

import java.util.*

interface ISoftDeletable {
    var deletedDate: Date?
    fun softDelete()
}
