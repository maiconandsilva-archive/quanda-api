package io.github.maiconandsilva.quanda.entities

import java.util.Date
import javax.persistence.*

interface Auditable {
    var createdDate: Date
    var lastModifiedDate: Date
}
