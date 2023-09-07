package io.github.maiconandsilva.quanda.schemas

import io.github.maiconandsilva.quanda.consts.Patterns
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class Login(
    @field:NotBlank
    var emailOrUsername: String,

    @field:Size(min = 8, max = 64)
    @field:Pattern(regexp = Patterns.PASSWORD)
    var password: String,
)

data class Token(val token: String)
data class UpdatePassword(val password: String)
data class UpdateUsername(val username: String)
data class UpdateEmail(val email: String)
