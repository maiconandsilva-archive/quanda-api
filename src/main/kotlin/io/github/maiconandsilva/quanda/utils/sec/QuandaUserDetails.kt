package io.github.maiconandsilva.quanda.utils.sec

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class QuandaUserDetails(
    val email: String?, username: String?, password: String?,
    authorities: MutableCollection<out GrantedAuthority?> = mutableListOf(),
    enabled: Boolean = true, accountNonExpired: Boolean = true,
    credentialsNonExpired: Boolean = true, accountNonLocked: Boolean = true,
) : User(
    username, password, enabled, accountNonExpired,
    credentialsNonExpired, accountNonLocked, authorities,
) {
    constructor(user: io.github.maiconandsilva.quanda.entities.User)
            : this(user.email, user.username ?: user.email, user.password)
}
