package com.freewill.service

import com.freewill.common.exception.AlreadyRegisterUserException
import com.freewill.dto.request.SignInRequest
import com.freewill.dto.request.SignUpRequest
import com.freewill.entity.User
import com.freewill.security.jwt.dto.JwtToken
import com.freewill.security.jwt.util.JwtProvider
import com.freewill.security.oauth.factory.AuthServiceFactory
import com.freewill.security.oauth.util.PrincipalUser
import com.freewill.security.oauth.util.PrincipalUserConverter
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.Objects.nonNull
import javax.naming.AuthenticationException

@Service
class AuthService(
    private val userService: UserService,
    private val profileService: ProfileService,
    private val authServiceFactory: AuthServiceFactory,
    private val principalUserConverter: PrincipalUserConverter,
    private val jwtProvider: JwtProvider,
) {
    @Transactional
    fun signUp(request: SignUpRequest): JwtToken {
        val providerId: String = authServiceFactory.getProviderId(request.provider, request.idToken)
        checkExistUser(providerId)

        val user: User = userService.save(request.toOAuth2Param(providerId))
        profileService.save(request.toProfileCreateParam(user))

        return jwtProvider.createJwtToken(getPrincipalUser(user))
    }

    fun signIn(request: SignInRequest): JwtToken {
        val providerId: String = authServiceFactory.getProviderId(request.provider, request.idToken)
        val user: User = userService.findByProviderId(providerId) ?: throw AuthenticationException()

        return jwtProvider.createJwtToken(getPrincipalUser(user))
    }

    fun getPrincipalUser(user: User): PrincipalUser = principalUserConverter.toPrincipalUser(user)

    fun checkExistUser(providerId: String) {
        val user: User? = userService.findByProviderId(providerId)

        if(nonNull(user)) {
            throw AlreadyRegisterUserException()
        }
    }
}
