package io.github.maiconandsilva.quanda.entities

import io.github.maiconandsilva.quanda.consts.Patterns
import java.util.*
import javax.persistence.*
import javax.validation.constraints.*

@Entity
@Table(schema = "users", name = "qa_user")
data class User(
    @field:Email
    @field:NotBlank
    @Column(unique = true, nullable = false)
    var email: String,

    @Column(length = 60, nullable = false)
    var password: String,

    @field:Size(min = 3, max = 64)
    @field:Pattern(
        regexp = Patterns.NO_ESPECIAL_CHARS,
        message = "username should have only letters, numbers, underscores and dashes")
    @Column(unique = true, length = 64)
    var username: String? = null,

    @field:Size(min = 2, max = 500)
    @Column(length = 500)
    var name: String? = null,

    @field:Max(2000)
    @Column(columnDefinition = "text")
    var about: String? = null,

    @field:Min(1)
    var reputation: Int = 1,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.DETACH])
    var reactions: MutableSet<UserReaction> = mutableSetOf(),

    @ManyToMany
    @JoinTable(schema = "users")
    val authorities: MutableSet<Authority> = mutableSetOf(),

) : AuditableEntity<UUID>()
