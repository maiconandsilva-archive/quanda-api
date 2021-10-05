package io.github.maiconandsilva.quanda.repositories

import io.github.maiconandsilva.quanda.entities.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository : JpaRepository<Tag, Long>
