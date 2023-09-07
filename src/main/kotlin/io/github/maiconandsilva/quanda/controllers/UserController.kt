package io.github.maiconandsilva.quanda.controllers

import com.fasterxml.jackson.annotation.JsonView
import io.github.maiconandsilva.quanda.annotations.Auth
import io.github.maiconandsilva.quanda.entities.User
import io.github.maiconandsilva.quanda.repositories.UserRepository
import io.github.maiconandsilva.quanda.schemas.*
import io.github.maiconandsilva.quanda.services.persistence.UserService
import io.github.maiconandsilva.quanda.utils.sec.QuandaUserDetails
import io.github.maiconandsilva.quanda.utils.sec.TokenManager
import io.github.maiconandsilva.quanda.utils.sec.Views
import io.github.maiconandsilva.quanda.utils.validation.Groups
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.annotation.CurrentSecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.ui.Model
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.util.UUID
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController(
    private val tokenManager: TokenManager,
    override val service: UserService,
) : SoftDeletableResourceController<UUID, User, UserRepository, UserService> {

    /**
     * @return Logged in user
     */
    @JsonView(Views.Owner::class)
    @GetMapping("/profile")
    fun getUserProfile(@AuthenticationPrincipal username: String, model: Model): User {
        return loadUser(username)
    }

    @PostMapping("/register")
    override fun create(@Validated(Groups.OnUpdate::class) @RequestBody entity: User): User
        = super.create(entity)

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody login: Login): Token {
        val auth = tokenManager.authenticate(login.emailOrUsername, login.password)
        return Token(tokenManager.generateSignedToken(auth))
    }

    @Validated(Groups.OnUpdate::class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/updatePassword")
    fun updatePassword(principal: Principal, @RequestBody updatePassword: UpdatePassword) {
        val user = loadUser(principal.name)
        user.password = updatePassword.password
        service.save(user)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/updateEmail")
    fun updateEmail(principal: Principal, @RequestBody updateEmail: UpdateEmail) {
        val user = loadUser(principal.name)
        user.email = updateEmail.email
        service.save(user)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/updateUsername")
    fun updateUsername(principal: Principal, @RequestBody updateUsername: UpdateUsername) {
        val user = loadUser(principal.name)
        user.username = updateUsername.username
        service.save(user)
    }

    private fun loadUser(subject: String): User = foundOr404(service.findByEmailOrUsername(subject))
}
