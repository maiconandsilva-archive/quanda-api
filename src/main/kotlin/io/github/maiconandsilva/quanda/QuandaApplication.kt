package io.github.maiconandsilva.quanda

import io.github.maiconandsilva.quanda.entities.User
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class QuandaApplication

fun main(args: Array<String>) {
	runApplication<QuandaApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
