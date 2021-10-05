package io.github.maiconandsilva.quanda.requests.filters

import io.github.maiconandsilva.quanda.utils.sec.TokenManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter(
    @Lazy private val tokenManager: TokenManager,
) : OncePerRequestFilter() {
    companion object {
        const val TOKEN_PREFIX = "Bearer "
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse,
                                  filterChain: FilterChain) {
        val authorization = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authorization != null) {
            val credentials = tokenManager.parseToken(authorization.removePrefix(TOKEN_PREFIX))
            SecurityContextHolder.getContext().authentication = credentials
        }
        filterChain.doFilter(request, response)
    }
}
