package io.github.maiconandsilva.quanda.entities

import com.fasterxml.jackson.annotation.JsonView
import io.github.maiconandsilva.quanda.consts.Patterns
import io.github.maiconandsilva.quanda.consts.Schema
import io.github.maiconandsilva.quanda.utils.sec.Views
import io.github.maiconandsilva.quanda.utils.validation.Groups
import java.util.*
import javax.persistence.*
import javax.validation.constraints.*

@Entity
@Table(schema = Schema.USER, name = "qa_user")
data class User(
    @JsonView(Views.Owner::class)
    @field:Email
    @field:NotBlank
    @Column(nullable = false, unique = true)
    var email: String,

    @Size(min = 8, max = 64, groups = [Groups.OnUpdate::class])
    @Pattern(regexp = Patterns.PASSWORD, groups = [Groups.OnUpdate::class])
    @Column(length = 60, nullable = false)
    var password: String,

    @JsonView(Views.Public::class)
    @field:Size(min = 3, max = 64)
    @field:Pattern(regexp = Patterns.NO_ESPECIAL_CHARS)
    @Column(length = 64, nullable = false, unique = true)
    var username: String,

    @JsonView(Views.Public::class)
    @field:Size(min = 2, max = 500)
    @Column(length = 500, nullable = false)
    var name: String,

    @JsonView(Views.Public::class)
    @field:Size(max = 2000)
    @Column(columnDefinition = "text")
    var about: String = String(),

    @JsonView(Views.Admin::class)
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(schema = Schema.USER)
    var authorities: MutableSet<Authority> = mutableSetOf(),

    @OneToOne
    @JoinColumn(
        name = "id", referencedColumnName = "user_id",
        updatable = false, insertable = false
    )
    var reputation: UserReputation,

    @JsonView(Views.Owner::class)
    @OneToMany(mappedBy = "subject")
    var events: MutableSet<EventLogging> = mutableSetOf(),

) : AuditableEntity<UUID>()
