package io.github.maiconandsilva.quanda.repositories

import org.springframework.data.jpa.repository.JpaRepository
import io.github.maiconandsilva.quanda.entities.UserReaction

interface UserReactionRepository : JpaRepository<UserReaction, Long>
