package com.freewill.dto.param

import com.freewill.enums.Provider

data class OAuth2Param(
    val provider: Provider,
    val providerId: String,
    val providerNickname: String,
    val providerEmail: String?
)
