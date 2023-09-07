package io.github.maiconandsilva.quanda.services.persistence

import io.github.maiconandsilva.quanda.entities.User
import io.github.maiconandsilva.quanda.repositories.UserRepository
import io.github.maiconandsilva.quanda.utils.sec.QuandaUserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.Throws

@Service
class UserService(
    private val authorityService: AuthorityService,
    private val passwordEncoder: PasswordEncoder,
    override val repository: UserRepository,
) : PersistenceService<UUID, User, UserRepository>, UserDetailsService {

    override fun create(entity: User): User {
        entity.password = passwordEncoder.encode(entity.password)
        return super.create(entity)
    }

    override fun update(id: UUID, userUpdate: User) {
        val user = repository.getById(id)
//        user.merge(userUpdate)
        save(user)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): QuandaUserDetails {
        // Either email or username will be used as unique identifier to load user
        val user = findByEmailOrUsername(username)
            ?: throw UsernameNotFoundException(
                "Couldn't found user with email or username: $username")
        return QuandaUserDetails(user)
    }

    fun findByEmailOrUsername(identifier: String): User? {
        return repository.findByEmailOrUsername(identifier, identifier)
    }
}
