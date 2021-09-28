package io.github.maiconandsilva.quanda.entities.embedables

import java.util.Date
import javax.persistence.*

interface IAuditable {
    var createdDate: Date
    var lastModifiedDate: Date  
}
