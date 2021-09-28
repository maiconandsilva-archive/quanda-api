package io.github.maiconandsilva.quanda.repositories

import io.github.maiconandsilva.quanda.entities.Answer
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AnswerRepository : JpaRepository<Answer, UUID>
