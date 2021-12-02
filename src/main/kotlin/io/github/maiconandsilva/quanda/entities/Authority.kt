package io.github.maiconandsilva.quanda.entities

import io.github.maiconandsilva.quanda.consts.Schema
import org.springframework.security.core.GrantedAuthority
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(schema = Schema.USER)
data class Authority(
    @Column(unique = true, length = 100)
    private val name: String
) : BaseEntity<Long>(), GrantedAuthority {
    override fun getAuthority(): String = name
}
