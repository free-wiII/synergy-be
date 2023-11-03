package com.freewill.domain.controller

import com.freewill.docs.ApiDocumentUtils.getDocumentRequest
import com.freewill.docs.ApiDocumentUtils.getDocumentResponse
import com.freewill.docs.RestDocsTest
import com.freewill.controller.AuthController
import com.freewill.dto.param.OAuth2Param
import com.freewill.dto.param.UserRegisterParam
import com.freewill.dto.request.SignInRequest
import com.freewill.dto.request.SignUpRequest
import com.freewill.entity.User
import com.freewill.enums.Provider
import com.freewill.service.AuthService
import com.freewill.security.jwt.dto.JwtToken
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.mock.web.MockMultipartFile
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(AuthController::class)
class AuthControllerTest : RestDocsTest() {

    @MockBean
    private lateinit var authService: AuthService

    private val image: MockMultipartFile = getMockMultiPartFile("image")

    private val provider: Provider = Provider.GOOGLE

    private val idToken: String = "eau1obo6aqbEq.hi3p7klKuOwkQ.Qui5Eibe3Qube4E"

    private val name: String = "synergy"

    private val email: String = "synergy@google.com"

    private val grantType: String = "Bearer "

    private lateinit var user: User

    private lateinit var jwtToken: JwtToken

    @BeforeEach
    fun setup() {
        user = User(
            oAuth2Param = OAuth2Param(
                provider = provider,
                providerId = "1234567890",
                providerNickname = name,
                providerEmail = email
            )
        )

        jwtToken = JwtToken(
            accessToken = "access token",
            refreshToken = "refresh token",
            user = user,
            grantType = grantType
        )
    }

    @Test
    fun `sign-up to use social login`() {
        // given
        val signUpRequest = SignUpRequest(
            provider = provider,
            idToken = idToken,
            name = name,
            email = email
        )

        given(authService.signUp(any(SignUpRequest::class.java)))
            .willReturn(jwtToken)

        val result: ResultActions = this.mockMvc.perform(
            post("/api/v1/auth/sign-up")
                .content(objectMapper.writeValueAsString(signUpRequest))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )

        result.andExpect(status().isOk)
            .andDo(
                document(
                    "sign-up",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestFields(
                        fieldWithPath("provider").type(JsonFieldType.STRING).description("소셜 제공자"),
                        fieldWithPath("idToken").type(JsonFieldType.STRING).description("identity 토큰"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일").optional()
                    ),
                    responseFields(
                        fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                        fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("액세스 토큰"),
                        fieldWithPath("data.refreshToken").type(JsonFieldType.STRING).description("리프레시 토큰")
                    )

                )
            )
    }

    @Test
    fun `sign-in to use social login`() {
        // given
        val signInRequest = SignInRequest(
            provider = provider,
            idToken = idToken,
        )

        given(authService.signIn(signInRequest))
            .willReturn(jwtToken)

        val result: ResultActions = this.mockMvc.perform(
            post("/api/v1/auth/sign-in")
                .content(toJson(signInRequest))
                .contentType(APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )

        result.andExpect(status().isOk)
            .andDo(
                document(
                    "sign-in",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestFields(
                        fieldWithPath("provider").type(JsonFieldType.STRING).description("소셜 제공자"),
                        fieldWithPath("idToken").type(JsonFieldType.STRING).description("identity 토큰"),
                    ),
                    responseFields(
                        fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                        fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("액세스 토큰"),
                        fieldWithPath("data.refreshToken").type(JsonFieldType.STRING).description("리프레시 토큰")
                    )

                )
            )
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}
