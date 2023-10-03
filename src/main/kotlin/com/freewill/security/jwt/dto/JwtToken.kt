package com.freewill.security.jwt.dto

import com.freewill.domain.user.dto.response.AuthResponse
import com.freewill.domain.user.entity.User

data class JwtToken(
    val accessToken: String,
    val refreshToken: String,
    val user: User,
    val grantType: String
) {
    fun toAuthResponse(): AuthResponse {
        return AuthResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}
