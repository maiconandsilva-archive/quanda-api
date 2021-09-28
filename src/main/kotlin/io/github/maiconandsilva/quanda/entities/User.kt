package io.github.maiconandsilva.quanda.entities

import io.github.maiconandsilva.quanda.consts.Patterns
import io.github.maiconandsilva.quanda.entities.embedables.IAuditable
import io.github.maiconandsilva.quanda.entities.embedables.Auditable
import io.github.maiconandsilva.quanda.entities.embedables.SoftDeletable
import io.github.maiconandsilva.quanda.entities.embedables.ISoftDeletable
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
    val email: String,

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
    @Column(length = 64)
    val username: String? = null,

    @Size(min = 2, max = 500)
    @Column(length = 500)
    val name: String? = null,

    @Max(2000)
    @Column(length = 2000)
    var about: String? = null,

    @Min(1)
    var reputation: Int = 1,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.DETACH])
    val reactions: MutableSet<UserReaction> = mutableSetOf(),

    @Embedded
    private val auditable: Auditable = Auditable(),

    @Embedded
    private val softDeletable: SoftDeletable = SoftDeletable(),

) : BaseEntity<UUID?>(), IAuditable by auditable, ISoftDeletable by softDeletable {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Column(length = 100, nullable = false)
    lateinit var hashedPassword: String,

    @PrePersist
    private fun saveHashedPassword() {
        if (password != null) {
            hashedPassword = passwordEncoder.encode(password)
        }
    }
}
