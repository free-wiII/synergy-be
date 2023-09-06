package com.freewill.domain.user.dto.request

import com.freewill.domain.user.dto.param.OAuth2Param
import com.freewill.domain.user.entity.enums.Provider

data class AuthRequest(
    val idToken: String,
    val name: String,
    val email: String?
) {
    fun toOAuth2Param(providerId: String): OAuth2Param {
        return OAuth2Param(
            provider = Provider.APPLE,
            providerId = providerId,
            providerNickname = name,
            providerEmail = email
        )
    }
}
