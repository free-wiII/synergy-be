package com.freewill.config.security

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.Key

@Configuration
class KeyConfig {
    @Value("\${jwt.secret}")
    private val jwtSecretKey: String? = null

    @Bean
    fun key(): Key {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey))
    }
}
