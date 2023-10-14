package com.freewill.dto.request

import com.freewill.enums.Provider

data class SignInRequest(
    val provider: Provider,
    val idToken: String
)
