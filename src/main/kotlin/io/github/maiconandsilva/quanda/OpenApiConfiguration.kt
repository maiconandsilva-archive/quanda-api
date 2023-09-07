package io.github.maiconandsilva.quanda

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.headers.Header
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.media.StringSchema
import io.swagger.v3.oas.models.parameters.Parameter
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.tags.Tag
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfiguration {
    @Bean fun springShopOpenAPI(): OpenAPI {
        return OpenAPI().info(
            Info()
            .title("SpringShop API")
            .description("Spring shop sample application")
            .version("v0.0.1")
            .license(License().name("Apache 2.0").url("http://springdoc.org"))
            .termsOfService("http://swagger.io/terms/")
        ).externalDocs(
            ExternalDocumentation()
            .description("SpringShop Wiki Documentation")
            .url("https://springshop.wiki.github.org/docs")
        ).components(
            Components()
            .addSecuritySchemes("bearer-key",
                SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
            ).addParameters("myHeader1",
                Parameter()
                .`in`("header")
                .schema(StringSchema())
                .name("myHeader1")
            ).addHeaders("myHeader2",
                Header()
                .description("myHeader2 header")
                .schema(StringSchema())
            )
        )
//            .addTagsItem(Tag().name("mytag"))
    }
}
