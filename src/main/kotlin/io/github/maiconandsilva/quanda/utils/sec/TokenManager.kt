package io.github.maiconandsilva.quanda.utils.sec


import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

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
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body
    }

    fun validateToken(token: String, userDetails: QuandaUserDetails) {
        val claims = getClaimsFrom(token)
        val isTokenExpired = claims.expiration.before(Date())
        if (claims.subject != userDetails.email || isTokenExpired) {
            throw CredentialsExpiredException("User token expired")
        }
    }

    fun generateSignedToken(auth: Authentication): String {
        val claims: MutableMap<String, Any> = mutableMapOf(
            "authorities" to auth.authorities,
        )

        val principal = auth.principal as UserDetails

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(principal.username as String)
            .setIssuer(issuer)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + tokenValidity))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun parseToken(token: String) : Authentication {
        val claims = getClaimsFrom(token)
        @Suppress("UNCHECKED_CAST")
        val authorities = claims["authorities"] as MutableCollection<out GrantedAuthority>
        return createAuthenticationToken(claims.subject, String(), authorities)
    }

    fun createAuthenticationToken(email: String, password: String,
                authorities: MutableCollection<out GrantedAuthority>? = null) : Authentication {
        if (authorities === null) {
            return authManager.authenticate(UsernamePasswordAuthenticationToken(email, password))
        }
        return UsernamePasswordAuthenticationToken(email, password, authorities)
    }
}
