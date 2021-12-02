package io.github.maiconandsilva.quanda.entities

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonView
import io.github.maiconandsilva.quanda.utils.sec.Views
import javax.persistence.*
import javax.validation.constraints.Size


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
)
@JsonSubTypes(
    JsonSubTypes.Type(Answer::class, name = "answer"),
    JsonSubTypes.Type(Question::class, name = "question"),
)
@Entity
@Table(schema = "posts")
@Inheritance(strategy = InheritanceType.JOINED)
open class Post(
    @field:Size(min = 100, max = 2000)
    @Column(columnDefinition = "text", nullable = false)
    @JsonView(Views.Public::class)
    open var text: String,

    @JsonView(Views.Public::class)
    @ManyToOne(optional = false)
    open var author: User,

    @field:Size(min = 1)
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "post")
    open var comments: MutableSet<Comment> = mutableSetOf(),

) : AuditableEntity<Long>()
