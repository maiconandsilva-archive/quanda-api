package io.github.maiconandsilva.quanda.utils.sec


import io.github.maiconandsilva.quanda.entities.Authority
import io.github.maiconandsilva.quanda.entities.User
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.ArrayList

@Component
class TokenManager(
    val authManager: AuthenticationManager,

    @Value("\${api.auth.jwt.issuer}")
    private val issuer: String,

    @Value("\${api.auth.jwt.secret}")
    private val jwtSecret: String,

    @Value("\${api.auth.jwt.token-validity}")
    private var tokenValidity: Long = 0,
) {
    private fun getClaimsFrom(token: String) : Claims {
        try {
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body
        } catch (e: ExpiredJwtException) {
            throw CredentialsExpiredException("Token expired")
        } catch (e: SignatureException) {
            throw BadCredentialsException("Ill formatted token")
        }
    }

    fun generateSignedToken(auth: Authentication): String {
        val claims: MutableMap<String, Any> = mutableMapOf(
            "authorities" to auth.authorities.map { it.authority },
        )

        val principal = auth.principal as UserDetails

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(principal.username)
            .setIssuer(issuer)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + tokenValidity))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun parseToken(token: String) : Authentication {
        val claims = getClaimsFrom(token)
        val authorities = claims.get("authorities", ArrayList<String>().javaClass)
        return createAuthenticationToken(claims.subject, null, authorities.map { Authority(it) })
    }

    fun createAuthenticationToken(email: String, password: String?,
            authorities: Collection<GrantedAuthority>? = null) : Authentication {
        return if (authorities === null) UsernamePasswordAuthenticationToken(email, password)
                else UsernamePasswordAuthenticationToken(email, password, authorities)
    }

    fun authenticate(email: String, password: String) : Authentication {
        return authManager.authenticate(createAuthenticationToken(email, password))
    }
}
