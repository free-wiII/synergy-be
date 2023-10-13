package com.freewill.domain.user.service

import com.freewill.domain.profile.dto.param.ProfileCreateParam
import com.freewill.domain.profile.service.ProfileService
import com.freewill.domain.user.dto.param.UserRegisterParam
import com.freewill.domain.user.dto.request.SignInRequest
import com.freewill.domain.user.dto.request.SignUpRequest
import com.freewill.domain.user.entity.User
import com.freewill.global.s3.S3Uploader
import com.freewill.global.s3.dto.S3UploadRequest
import com.freewill.security.jwt.dto.JwtToken
import com.freewill.security.jwt.util.JwtProvider
import com.freewill.security.oauth.factory.AuthServiceFactory
import com.freewill.security.oauth.util.PrincipalUser
import com.freewill.security.oauth.util.PrincipalUserConverter
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import javax.naming.AuthenticationException

@Service
class AuthService(
    private val userService: UserService,
    private val profileService: ProfileService,
    private val authServiceFactory: AuthServiceFactory,
    private val principalUserConverter: PrincipalUserConverter,
    private val jwtProvider: JwtProvider,
    private val s3Uploader: S3Uploader
) {
    @Transactional
    fun register(param: UserRegisterParam): JwtToken {
        val jwtToken: JwtToken = signUp(param.signUpRequest)
        val user: User = jwtToken.user

        val imageUri: String? = param.image?.let {
            s3Uploader.upload(S3UploadRequest(param.image, user.id!!))
        }

        profileService.save(
            ProfileCreateParam(
                imageUri,
                user,
                param.signUpRequest.name,
                param.signUpRequest.email
            )
        )

        return jwtToken
    }

    fun signUp(request: SignUpRequest): JwtToken {
        val providerId: String = authServiceFactory.getProviderId(request.provider, request.idToken)
        val user: User = userService.save(request.toOAuth2Param(providerId))

        return jwtProvider.createJwtToken(getPrincipalUser(user))
    }

    fun signIn(request: SignInRequest): JwtToken {
        val providerId: String = authServiceFactory.getProviderId(request.provider, request.idToken)
        val user: User = userService.findByProviderId(providerId) ?: throw AuthenticationException()

        return jwtProvider.createJwtToken(getPrincipalUser(user))
    }

    fun getPrincipalUser(user: User): PrincipalUser = principalUserConverter.toPrincipalUser(user)
}
