package io.github.maiconandsilva.quanda.entities

import io.github.maiconandsilva.quanda.consts.Patterns
import java.util.UUID
import javax.persistence.*
import javax.validation.constraints.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
@Table(schema = "users", name = "qa_user")
data class User(
    @Email
    @Column(unique = true, nullable = false)
    var email: String,

    @Size(min = 8, max = 64)
    @Pattern(
        regexp = Patterns.PASSWORD,
        message = "{quanda.user.validation.password}")
    @Transient
    var password: String? = null,

    @Size(min = 3, max = 64)
    @Pattern(
        regexp = Patterns.NO_ESPECIAL_CHARS,
        message = "username should have only letters, numbers, underscores and dashes")
    @Column(unique = true, length = 64)
    var username: String? = null,

    @Size(min = 2, max = 500)
    @Column(length = 500)
    var name: String? = null,

    @Max(2000)
    @Column(columnDefinition = "text")
    var about: String? = null,

    @Min(1)
    var reputation: Int = 1,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.DETACH])
    var reactions: MutableSet<UserReaction> = mutableSetOf(),

) : AuditableEntity<UUID>() {
    @Column(name = "password", length = 100, nullable = false)
    lateinit var hashedPassword: String
}
