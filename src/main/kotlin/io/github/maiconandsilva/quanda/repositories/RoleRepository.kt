package io.github.maiconandsilva.quanda.repositories

import io.github.maiconandsilva.quanda.entities.Role
import io.github.maiconandsilva.quanda.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(name: String): Role
}
