package io.github.maiconandsilva.quanda.repositories

import io.github.maiconandsilva.quanda.entities.Post
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PostRepository : JpaRepository<Post, Long>
