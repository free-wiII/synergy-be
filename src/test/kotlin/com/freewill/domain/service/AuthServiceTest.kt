package com.freewill.domain.service

import com.freewill.service.ProfileService
import com.freewill.dto.param.OAuth2Param
import com.freewill.dto.request.SignUpRequest
import com.freewill.entity.User
import com.freewill.enums.Provider
import com.freewill.service.AuthService
import com.freewill.service.UserService
import com.freewill.s3.S3Uploader
import com.freewill.security.jwt.dto.JwtToken
import com.freewill.security.jwt.util.JwtProvider
import com.freewill.security.oauth.factory.AuthServiceFactory
import com.freewill.security.oauth.util.PrincipalUser
import com.freewill.security.oauth.util.PrincipalUserConverter
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.util.ReflectionTestUtils

@ExtendWith(MockitoExtension::class)
class AuthServiceTest {
    @InjectMocks
    private lateinit var authService: AuthService

    @Mock
    private lateinit var userService: UserService

    @Mock
    private lateinit var profileService: ProfileService

    @Mock
    private lateinit var authServiceFactory: AuthServiceFactory

    @Mock
    private lateinit var principalUserConverter: PrincipalUserConverter

    @Mock
    private lateinit var jwtProvider: JwtProvider

    @Mock
    private lateinit var s3Uploader: S3Uploader

    private lateinit var authRequest: SignUpRequest
    private lateinit var oAuth2Param: OAuth2Param
    private lateinit var user: User
    private lateinit var principalUser: PrincipalUser
    private val providerId: String = "providerId"
    private lateinit var jwtToken: JwtToken

    @BeforeEach
    fun setup() {
        authRequest = SignUpRequest(
            provider = Provider.APPLE,
            idToken = "id token",
            name = "name",
            email = "email"
        )
        oAuth2Param = authRequest.toOAuth2Param(providerId)
        user = User(oAuth2Param)
        ReflectionTestUtils.setField(user, "id", 1L)

        principalUser = PrincipalUser(user, mutableMapOf("id" to user.id!!))

        jwtToken = JwtToken("Access Token", "Refresh Token", user, "Bearer")
    }

    @Test
    fun `test`() {
        println("hi")
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}
