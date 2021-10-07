package io.github.maiconandsilva.quanda.services.persistence

import io.github.maiconandsilva.quanda.entities.Authority
import io.github.maiconandsilva.quanda.repositories.AuthorityRepository
import org.springframework.stereotype.Service

@Service
class AuthorityService(
    override val repository: AuthorityRepository,
) : PersistenceService<Long, Authority, AuthorityRepository>
