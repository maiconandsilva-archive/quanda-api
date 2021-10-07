package io.github.maiconandsilva.quanda.entities

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonView
import io.github.maiconandsilva.quanda.utils.sec.Views
import org.springframework.data.jpa.domain.AbstractPersistable
import java.io.Serializable
import javax.persistence.MappedSuperclass

@JsonInclude(JsonInclude.Include.NON_NULL)
@MappedSuperclass
abstract class BaseEntity<ID : Serializable?> : AbstractPersistable<ID>() {
    @JsonView(Views.Public::class)
    override fun getId(): ID? = super.getId()

    @JsonView(Views.Private::class)
    override fun isNew(): Boolean = super.isNew()
}
