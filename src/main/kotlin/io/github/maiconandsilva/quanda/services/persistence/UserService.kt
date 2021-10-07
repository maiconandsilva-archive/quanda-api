package io.github.maiconandsilva.quanda.services.persistence

import io.github.maiconandsilva.quanda.entities.User
import io.github.maiconandsilva.quanda.repositories.UserRepository
import io.github.maiconandsilva.quanda.utils.sec.QuandaUserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val authorityService: AuthorityService,
    private val passwordEncoder: PasswordEncoder,
    override val repository: UserRepository,
) : PersistenceService<UUID, User, UserRepository>, UserDetailsService {

    override fun create(entity: User): User {
        entity.password = passwordEncoder.encode(entity.password)
        entity.authorities += authorityService.repository.findByName("ROLE_USER")
        return super.create(entity)
    }

    override fun loadUserByUsername(subject: String): QuandaUserDetails {
        // Email will be used as unique identifier to load user
        val user = repository.findByEmailOrUsername(subject, subject)
            ?: throw UsernameNotFoundException(
                "Couldn't found user with email or username: $subject")
        return QuandaUserDetails(user)
    }
}
