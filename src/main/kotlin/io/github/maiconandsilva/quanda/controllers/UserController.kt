package io.github.maiconandsilva.quanda.controllers

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import io.github.maiconandsilva.quanda.entities.User
import io.github.maiconandsilva.quanda.repositories.UserRepository
import io.github.maiconandsilva.quanda.services.persistence.UserService
import io.github.maiconandsilva.quanda.utils.sec.TokenManager
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.util.UUID

data class Login(
    var email: String,
    var password: String,
    var token: String? = null,
)

@RestController
@RequestMapping("/user")
class UserController(
    val tokenManager: TokenManager,
    override val service: UserService,
) : ResourceController<UUID, User, UserRepository, UserService> {

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody login: Login) : Login {
        val auth = tokenManager.createAuthenticationToken(login.email, login.password)
        login.token = tokenManager.generateSignedToken(auth)
        login.password = ""
        return login
    }
}
