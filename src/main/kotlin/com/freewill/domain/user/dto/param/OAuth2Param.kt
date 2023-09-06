package com.freewill.domain.user.dto.param

import com.freewill.domain.user.entity.enums.Provider

data class OAuth2Param(
    val provider: Provider,
    val providerId: String,
    val providerNickname: String,
    val providerEmail: String?
)
