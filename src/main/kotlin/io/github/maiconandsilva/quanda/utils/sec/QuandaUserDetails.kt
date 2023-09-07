package io.github.maiconandsilva.quanda.utils.sec

import io.github.maiconandsilva.quanda.entities.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class QuandaUserDetails(val loggedInUser: User) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = loggedInUser.authorities
    override fun getPassword(): String = loggedInUser.password
    override fun getUsername(): String?
        = if (loggedInUser.username.isEmpty()) loggedInUser.email else loggedInUser.username
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = loggedInUser.deletedDate == null
}
