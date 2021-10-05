package io.github.maiconandsilva.quanda

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class Beans {
    @Bean fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}
