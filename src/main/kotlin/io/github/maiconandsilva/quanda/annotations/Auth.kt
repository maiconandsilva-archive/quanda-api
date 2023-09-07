package io.github.maiconandsilva.quanda.annotations

import org.springframework.security.core.annotation.AuthenticationPrincipal


@AuthenticationPrincipal(expression="loggedInUser")
annotation class Auth
