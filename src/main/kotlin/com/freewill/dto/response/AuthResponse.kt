package com.freewill.domain.user.dto.response

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String
)
