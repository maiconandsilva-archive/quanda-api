package io.github.maiconandsilva.quanda.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(schema = "users")
data class Role(@Column(unique = true, length = 25) val name: String) : BaseEntity<Long>()
