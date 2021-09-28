package io.github.maiconandsilva.quanda.repositories

import io.github.maiconandsilva.quanda.entities.Comment
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CommentRepository : JpaRepository<Comment, UUID>
