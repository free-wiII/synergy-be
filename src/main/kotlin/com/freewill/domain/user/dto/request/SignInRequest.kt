package com.freewill.domain.user.dto.request

import com.freewill.domain.user.entity.enums.Provider

data class SignInRequest(
    val provider: Provider,
    val idToken: String
)
