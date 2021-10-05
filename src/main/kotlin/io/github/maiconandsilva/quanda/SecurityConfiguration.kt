package io.github.maiconandsilva.quanda

import io.github.maiconandsilva.quanda.requests.filters.JwtAuthenticationEntryPoint
import io.github.maiconandsilva.quanda.requests.filters.JwtAuthenticationFilter
import io.github.maiconandsilva.quanda.services.persistence.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration(
    private val userDetailsService: UserService,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val authenticationEntryPoint: JwtAuthenticationEntryPoint,

    @Value("\${api.auth.unauthenticated-paths}")
    private val unauthenticatedPaths: List<String>,
) : WebSecurityConfigurerAdapter() {

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager =
        super.authenticationManagerBean()

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .cors().disable()

        http.authorizeRequests()
            .antMatchers(*unauthenticatedPaths.toTypedArray()).permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(
                jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)

        // this disables session creation on Spring Security
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    public override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
    }
}

