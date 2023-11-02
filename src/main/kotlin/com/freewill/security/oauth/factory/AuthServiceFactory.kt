package com.freewill.security.oauth.factory

import com.freewill.common.exception.SynergyException
import com.freewill.enums.Provider
import com.freewill.enums.ReturnCode
import com.freewill.security.oauth.service.AppleAuthService
import com.freewill.security.oauth.service.GoogleAuthService
import com.freewill.security.oauth.service.KakaoAuthService
import com.freewill.security.oauth.service.SocialAuthService
import org.springframework.stereotype.Component
import java.util.EnumMap

@Component
class AuthServiceFactory(
    private val appleAuthService: AppleAuthService,
    private val kakaoAuthService: KakaoAuthService,
    private val googleAuthService: GoogleAuthService
) {
    private val authServiceMap: MutableMap<Provider, SocialAuthService> = EnumMap(Provider::class.java)

    init {
        initialize()
    }

    private fun initialize() {
        authServiceMap[Provider.APPLE] = appleAuthService
        authServiceMap[Provider.KAKAO] = kakaoAuthService
        authServiceMap[Provider.GOOGLE] = googleAuthService
    }

    fun getProviderId(provider: Provider, idToken: String): String {
        return getAuthService(provider).getProviderId(idToken)
    }

    fun getAuthService(provider: Provider): SocialAuthService {
        return authServiceMap[provider] ?: throw SynergyException(ReturnCode.WRONG_PROVIDER)
    }
}
