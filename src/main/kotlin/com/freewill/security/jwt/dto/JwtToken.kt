package com.freewill.security.jwt.dto

import com.freewill.domain.user.dto.response.AuthResponse

data class JwtToken(
    val accessToken: String,
    val refreshToken: String,
    val grantType: String
) {
    fun toAuthResponse(): AuthResponse {
        return AuthResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}
