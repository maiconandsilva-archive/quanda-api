package io.github.maiconandsilva.quanda.entities

import org.springframework.data.jpa.domain.AbstractPersistable
import java.io.Serializable
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntity<ID : Serializable?> : AbstractPersistable<ID>()
