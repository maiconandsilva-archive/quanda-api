package io.github.maiconandsilva.quanda.services.persistence

import io.github.maiconandsilva.quanda.entities.Post
import io.github.maiconandsilva.quanda.repositories.PostRepository
import org.springframework.stereotype.Service

@Service
class PostService(
    override val repository: PostRepository,
) : PersistenceService<Long, Post, PostRepository> {
    // override fun create(id: UUID) {

    // }

    // override fun update(id: UUID) : Post {

    // }

    // override fun delete(id: UUID) {

    // }
}
