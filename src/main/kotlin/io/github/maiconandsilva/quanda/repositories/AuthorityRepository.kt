package io.github.maiconandsilva.quanda.repositories

import io.github.maiconandsilva.quanda.entities.Authority
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorityRepository : JpaRepository<Authority, Long> {
    fun findByName(name: String): Authority
}
