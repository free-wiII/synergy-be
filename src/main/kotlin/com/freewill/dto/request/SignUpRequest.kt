package com.freewill.dto.request

import com.freewill.dto.param.OAuth2Param
import com.freewill.dto.param.ProfileCreateParam
import com.freewill.entity.User
import com.freewill.enums.Provider

data class SignUpRequest(
    val provider: Provider,
    val idToken: String,
    val name: String,
    val email: String?
) {
    fun toOAuth2Param(providerId: String): OAuth2Param {
        return OAuth2Param(
            provider = provider,
            providerId = providerId,
            providerNickname = name,
            providerEmail = email
        )
    }

    fun toProfileCreateParam(user: User) = ProfileCreateParam(user, name, email)
}
