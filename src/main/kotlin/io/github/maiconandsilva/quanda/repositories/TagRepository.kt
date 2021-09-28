package io.github.maiconandsilva.quanda.repositories

import io.github.maiconandsilva.quanda.entities.Tag
import org.springframework.data.repository.CrudRepository

interface TagRepository : CrudRepository<Tag, Long>
