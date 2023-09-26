package com.freewill.domain.user.service

import com.freewill.domain.user.dto.param.OAuth2Param
import com.freewill.domain.user.dto.request.AuthRequest
import com.freewill.security.jwt.dto.JwtToken
import com.freewill.security.jwt.util.JwtProvider
import com.freewill.security.oauth.factory.AuthServiceFactory
import com.freewill.security.oauth.util.PrincipalUser
import com.freewill.security.oauth.util.PrincipalUserConverter
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userService: UserService,
    private val authServiceFactory: AuthServiceFactory,
    private val principalUserConverter: PrincipalUserConverter,
    private val jwtProvider: JwtProvider
) {
    fun signUp(request: AuthRequest): JwtToken {
        val providerId: String = authServiceFactory.getProviderId(request.provider, request.idToken)
        val principalUser: PrincipalUser = getPrincipalUser(request.toOAuth2Param(providerId))

        return jwtProvider.createJwtToken(principalUser)
    }

    fun getPrincipalUser(oAuth2Param: OAuth2Param): PrincipalUser {
        userService.findByProviderId(oAuth2Param.providerId)?.let {
            return principalUserConverter.toPrincipalUser(it)
        }

        return principalUserConverter.toPrincipalUser(userService.save(oAuth2Param))
    }
}
