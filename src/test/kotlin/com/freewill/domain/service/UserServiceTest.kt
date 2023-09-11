package com.freewill.domain.service

import com.freewill.domain.user.dto.param.OAuth2Param
import com.freewill.domain.user.entity.User
import com.freewill.domain.user.entity.enums.Provider
import com.freewill.domain.user.repository.UserRepository
import com.freewill.domain.user.service.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.util.ReflectionTestUtils
import java.lang.NullPointerException
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class UserServiceTest {
    @InjectMocks
    private lateinit var userService: UserService

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var oAuth2Param: OAuth2Param
    private lateinit var user: User

    @BeforeEach
    fun setup() {
        oAuth2Param = OAuth2Param(
            provider = Provider.APPLE,
            providerId = "providerId",
            providerEmail = "providerEmail",
            providerNickname = "providerNickname"
        )
        user = User(oAuth2Param)
        ReflectionTestUtils.setField(user, "id", 1L)
    }

    @Test
    fun `save new user`() {
        // given
        given(userRepository.save(any(User::class.java)))
            .willReturn(user)

        // when
        val savedUser = userService.save(oAuth2Param)

        // then
        assertEquals(user, savedUser)
    }

    @Test
    fun `find by id successfully`() {
        // given
        given(userRepository.findById(anyLong()))
            .willReturn(Optional.of(user))

        // when
        val findUser = userService.findById(1L)

        // then
        assertEquals(user, findUser)
    }

    @Test
    fun `find by id fail`() {
        // given
        given(userRepository.findById(anyLong()))
            .willReturn(null)

        // when
        assertThrows(NullPointerException::class.java) {
            userService.findById(1L)
        }
    }

    @Test
    fun `find by provider id successfully`() {
        // given
        given(userRepository.findByProviderId(anyString()))
            .willReturn(user)

        // when
        val findUser = userService.findByProviderId("provider id")

        // then
        assertEquals(user, findUser)
    }

    @Test
    fun `find by provider id fail`() {
        // given
        given(userRepository.findByProviderId(anyString()))
            .willReturn(null)

        // when
        val findUser = userService.findByProviderId("provider id")

        // then
        assertEquals(null, findUser)
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}
