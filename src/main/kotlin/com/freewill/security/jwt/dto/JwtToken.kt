package com.freewill.security.jwt.dto

import com.freewill.dto.response.AuthResponse
import com.freewill.entity.User

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
