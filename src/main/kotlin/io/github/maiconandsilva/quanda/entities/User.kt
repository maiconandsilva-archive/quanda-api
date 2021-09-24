package io.github.maiconandsilva.quanda.entities

import javax.persistence.*
import javax.validation.constraints.*

@Entity
@Table(name = "qa_user")
data class User(
    @Email
    val email: String,

//    @Pattern(regexp = ".*[a-z]+.*")
    @Size(min = 8, max = 64)
    val password: String,

    @Size(min = 3, max = 64)
    @Pattern(
        regexp = Patterns.NO_ESPECIAL_CHARS,
        message = "username should have only letters, "
                + "numbers, underscores and dashes"
    )
    val username: String? = null,

    @Size(min = 2, max = 500)
    val name: String? = null,

    @Max(2000)
    var about: String? = null,

    @Min(1)
    var reputation: Int = 1,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    val reactions: MutableSet<UserReaction> = mutableSetOf(),
) : BaseEntity() {

    @Embedded
    private lateinit var timestampRecord: TimestampRecord

}
