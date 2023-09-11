package com.freewill.security.oauth.service

import com.freewill.domain.user.entity.enums.Provider
import com.freewill.global.common.exception.BadRequestException
import org.springframework.stereotype.Component
import java.util.EnumMap

@Component
class AuthServiceFactory(
    private val appleAuthService: AppleAuthService
) {
    private val authServiceMap: MutableMap<Provider, SocialAuthService> = EnumMap(Provider::class.java)

    init {
        initialize()
    }

    private fun initialize() {
        authServiceMap[Provider.APPLE] = appleAuthService
    }

    fun getProviderId(provider: Provider, idToken: String): String {
        return getAuthService(provider).getProviderId(idToken)
    }

    fun getAuthService(provider: Provider): SocialAuthService {
        return authServiceMap[provider] ?: throw BadRequestException()
    }
}
