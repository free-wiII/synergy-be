package com.freewill.security.jwt.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.freewill.entity.User
import com.freewill.service.UserService
import com.freewill.security.oauth.util.PrincipalUser
import com.freewill.security.oauth.util.PrincipalUserConverter
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.security.PublicKey
import java.util.Base64

@Component
class JwtValidator(
    private val key: Key,
    private val userService: UserService,
    private val principalUserConverter: PrincipalUserConverter,
) {
    fun getAuthentication(token: String): Authentication {
        val claims = getTokenClaims(token)
        val user: User = userService.findById(claims.get("id", String::class.java).toLong())
        val principalUser: PrincipalUser = principalUserConverter.toPrincipalUser(user)

        return UsernamePasswordAuthenticationToken(principalUser, "", principalUser.authorities)
    }

    @Throws(JsonProcessingException::class)
    fun parseHeaders(token: String): Map<String, String> {
        val header = token.split("\\.")[0]
        return ObjectMapper().readValue(
            decodeHeader(header),
            MutableMap::class.java
        ) as Map<String, String>
    }

    fun decodeHeader(token: String?): String? {
        return String(Base64.getDecoder().decode(token), StandardCharsets.UTF_8)
    }

    fun getTokenClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun getTokenClaims(token: String?, publicKey: PublicKey?): Claims? {
        return Jwts.parserBuilder()
            .setSigningKey(publicKey)
            .build()
            .parseClaimsJws(token)
            .body
    }
}
