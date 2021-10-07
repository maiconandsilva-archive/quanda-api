package io.github.maiconandsilva.quanda.entities

import org.springframework.security.core.GrantedAuthority
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(schema = "users")
data class Authority(
    @Column(unique = true, length = 25)
    val name: String
) : BaseEntity<Long>(), GrantedAuthority {
    override fun getAuthority(): String = name
}
