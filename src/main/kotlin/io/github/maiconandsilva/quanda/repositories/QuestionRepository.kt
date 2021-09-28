package io.github.maiconandsilva.quanda.repositories

import io.github.maiconandsilva.quanda.entities.Question
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface QuestionRepository : JpaRepository<Question, UUID>
