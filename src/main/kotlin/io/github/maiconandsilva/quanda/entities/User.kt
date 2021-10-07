package io.github.maiconandsilva.quanda.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonView
import io.github.maiconandsilva.quanda.consts.Patterns
import io.github.maiconandsilva.quanda.utils.sec.Views
import io.github.maiconandsilva.quanda.utils.validation.Groups
import java.util.*
import javax.persistence.*
import javax.validation.constraints.*

@Entity
@Table(schema = "users", name = "qa_user")
data class User(
    @JsonView(Views.OwnerAdmin::class, Views.User.UpdateEmail::class)
    @field:Email
    @field:NotBlank
    @Column(unique = true, nullable = false)
    var email: String,

    @JsonView(Views.User.UpdatePassword::class)
    @Size(min = 8, max = 64, groups = [Groups.OnUpdate::class])
    @Pattern(regexp = Patterns.PASSWORD, groups = [Groups.OnUpdate::class])
    @Column(length = 60, nullable = false)
    var password: String,

    @JsonView(Views.Public::class, Views.User.UpdateUsername::class)
    @field:Size(min = 3, max = 64)
    @field:Pattern(regexp = Patterns.NO_ESPECIAL_CHARS)
    @Column(unique = true, length = 64)
    var username: String? = null,

    @JsonView(Views.Public::class)
    @field:Size(min = 2, max = 500)
    @Column(length = 500)
    var name: String? = null,

    @JsonView(Views.Public::class)
    @field:Size(max = 2000)
    @Column(columnDefinition = "text")
    var about: String? = null,

    @JsonView(Views.Public::class)
    @field:Min(1)
    var reputation: Int = 1,

    @JsonView(Views.Owner::class)
    @OneToMany(mappedBy = "user")
    var reactions: MutableSet<UserReaction> = mutableSetOf(),

    @JsonView(Views.Admin::class)
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(schema = "users")
    val authorities: MutableSet<Authority> = mutableSetOf(),

) : AuditableEntity<UUID>()
