package io.github.maiconandsilva.quanda.utils.sec

import io.github.maiconandsilva.quanda.entities.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.User as SpringSecurityUser
import java.util.*

class SecurityUserDetails(
    val appUser: User,
    username: String,
    password: String,
    enabled: Boolean = true,
    accountNonExpired: Boolean = true,
    credentialsNonExpired: Boolean = true,
    accountNonLocked: Boolean = true,
    authorities: MutableCollection<out GrantedAuthority?> = mutableListOf(),
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        TODO("Not yet implemented")
    }
    
    override fun getPassword(): String = appUser.password
    override fun getUsername(): String? = appUser.username
    override fun isAccountNonExpired(): Boolean = isEnabled
    override fun isAccountNonLocked(): Boolean = isEnabled
    override fun isCredentialsNonExpired(): Boolean = isEnabled
    override fun isEnabled(): Boolean = appUser.deletedDate == null
}
