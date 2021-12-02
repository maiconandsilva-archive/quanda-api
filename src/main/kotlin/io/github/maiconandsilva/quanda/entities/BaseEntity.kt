package io.github.maiconandsilva.quanda.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.data.jpa.domain.AbstractPersistable
import java.io.Serializable
import javax.persistence.MappedSuperclass

@JsonIgnoreProperties("new")
@JsonInclude(JsonInclude.Include.NON_NULL)
@MappedSuperclass
abstract class BaseEntity<ID : Serializable?> : AbstractPersistable<ID>()
