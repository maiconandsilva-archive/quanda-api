package io.github.maiconandsilva.quanda.requests.filters

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.Serializable
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationEntryPoint(
    @Value("\${api.auth.uri}") private val authUri: String,
) : AuthenticationEntryPoint, Serializable {
    override fun commence(request: HttpServletRequest, response: HttpServletResponse,
                        authException: AuthenticationException) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
            "Authorization header must be set with Bearer token"
            + " obtained at ${request.contextPath}${authUri}")
    }
}
