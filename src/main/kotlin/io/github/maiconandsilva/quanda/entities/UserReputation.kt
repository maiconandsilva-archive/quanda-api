package io.github.maiconandsilva.quanda.entities

import io.github.maiconandsilva.quanda.consts.Schema
import org.hibernate.annotations.Immutable
import java.util.*
import javax.persistence.*

@Entity
@Immutable
@AttributeOverride(name = "id", column = Column(name = "user_id"))
@Table(schema = Schema.USER, name = "user_reputation")
class UserReputation : BaseEntity<UUID>() {
    var reputation: Int = 0
}
