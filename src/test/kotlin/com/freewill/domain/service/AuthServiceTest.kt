package com.freewill.domain.service

import com.freewill.domain.user.dto.param.OAuth2Param
import com.freewill.domain.user.dto.request.AuthRequest
import com.freewill.domain.user.entity.User
import com.freewill.domain.user.entity.enums.Provider
import com.freewill.domain.user.service.AuthService
import com.freewill.domain.user.service.UserService
import com.freewill.security.jwt.dto.JwtToken
import com.freewill.security.jwt.util.JwtProvider
import com.freewill.security.oauth.service.AuthServiceFactory
import com.freewill.security.oauth.util.PrincipalUser
import com.freewill.security.oauth.util.PrincipalUserConverter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
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
    private lateinit var authServiceFactory: AuthServiceFactory

    @Mock
    private lateinit var principalUserConverter: PrincipalUserConverter

    @Mock
    private lateinit var jwtProvider: JwtProvider

    private lateinit var authRequest: AuthRequest
    private lateinit var oAuth2Param: OAuth2Param
    private lateinit var user: User
    private lateinit var principalUser: PrincipalUser
    private val providerId: String = "providerId"
    private lateinit var jwtToken: JwtToken

    @BeforeEach
    fun setup() {
        authRequest = AuthRequest(
            provider = Provider.APPLE,
            idToken = "id token",
            name = "name",
            email = "email"
        )
        oAuth2Param = authRequest.toOAuth2Param(providerId)
        user = User(oAuth2Param)
        ReflectionTestUtils.setField(user, "id", 1L)

        principalUser = PrincipalUser(user, mutableMapOf("id" to user.id!!), user.getAuthorities())
        given(principalUserConverter.toPrincipalUser(user))
            .willReturn(principalUser)

        jwtToken = JwtToken("Access Token", "Refresh Token", "Bearer")
    }

    @Test
    fun `get PrincipalUser from an already existing user`() {
        // given
        given(userService.findByProviderId(providerId))
            .willReturn(user)

        // when
        val existPrincipalUser: PrincipalUser = authService.getPrincipalUser(oAuth2Param)

        // then
        assertEquals(user.id.toString(), existPrincipalUser.name)
    }

    @Test
    fun `get principalUser from new user`() {
        // given
        given(userService.findByProviderId(any(String::class.java)))
            .willReturn(null)
        given(userService.save(oAuth2Param))
            .willReturn(user)

        // when
        val newPrincipalUser: PrincipalUser = authService.getPrincipalUser(oAuth2Param)

        // then
        assertEquals(user.id.toString(), newPrincipalUser.name)
    }

    @Test
    fun `sign up successfully`() {
        // given
        given(authServiceFactory.getProviderId(authRequest.provider, authRequest.idToken))
            .willReturn(providerId)
        given(userService.findByProviderId(providerId))
            .willReturn(user)
        given(jwtProvider.createJwtToken(principalUser))
            .willReturn(jwtToken)

        // when
        val generatedJwtToken: JwtToken = authService.signUp(authRequest)

        // then
        assertEquals(jwtToken, generatedJwtToken)
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}
