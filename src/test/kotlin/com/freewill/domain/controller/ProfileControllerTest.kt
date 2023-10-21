package com.freewill.domain.controller

import com.freewill.docs.ApiDocumentUtils.getDocumentRequest
import com.freewill.docs.ApiDocumentUtils.getDocumentResponse
import com.freewill.docs.RestDocsTest
import com.freewill.controller.ProfileController
import com.freewill.dto.request.ProfileUpdateRequest
import com.freewill.entity.Profile
import com.freewill.service.ProfileService
import com.freewill.dto.param.OAuth2Param
import com.freewill.entity.User
import com.freewill.enums.Provider
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.mock.web.MockMultipartFile
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestPartBody
import org.springframework.restdocs.payload.PayloadDocumentation.requestPartFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ProfileController::class)
class ProfileControllerTest : RestDocsTest() {

    @MockBean
    private lateinit var profileService: ProfileService

    private lateinit var user: User

    private lateinit var profile: Profile

    private val image: MockMultipartFile = getMockMultiPartFile("image")

    private val name: String = "synergy"

    private val email: String = "synergy@google.com"

    @BeforeEach
    fun setup() {
        user = User(
            oAuth2Param = OAuth2Param(
                provider = Provider.APPLE,
                providerId = "1234567890",
                providerNickname = name,
                providerEmail = email
            )
        )

        profile = Profile(
            user = user,
            imageUri = "image uri",
            nickname = "synergy",
            email = "synergy@synergy.com"
        )

        given(profileService.findByUser(any(User::class.java)))
            .willReturn(profile)
    }

    @Test
    fun `success to search profile`() {
        // then
        val result: ResultActions = this.mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/v1/profiles")
                .contentType(APPLICATION_JSON)
                .header("Authorization", "access token")
                .characterEncoding("UTF-8")
        )

        result.andExpect(status().isOk)
            .andDo(
                document(
                    "profile-detail",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestHeaders(
                        headerWithName("Authorization").description("액세스 토큰")
                    ),
                    responseFields(
                        fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                        fieldWithPath("data.imageUri").type(JsonFieldType.STRING).description("프로필 사진 경로").optional(),
                        fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("닉네임"),
                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일").optional()
                    )

                )
            )
    }

    @Test
    fun `success to update profile`() {
        // given
        val profileUpdateRequest = ProfileUpdateRequest(
            nickname = name,
            email = email
        )

        val result: ResultActions = this.mockMvc.perform(
            multipart("/api/v1/profiles")
                .file(image)
                .file(toMultiPartFile("profileUpdateRequest", objectMapper.writeValueAsString(profileUpdateRequest)))
                .header("Authorization", "access token")
                .contentType("multipart/form-data")
                .with { request ->
                    request.method = "PUT"
                    request
                }
        )

        result.andExpect(status().isOk)
            .andDo(
                document(
                    "profile-modify",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestHeaders(
                        headerWithName("Authorization").description("액세스 토큰")
                    ),
                    requestPartBody("image"),
                    requestPartFields(
                        "profileUpdateRequest",
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("이름").optional(),
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일").optional()
                    ),
                    responseFields(
                        fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                        fieldWithPath("data").type(JsonFieldType.STRING).description("응답 데이터").ignored()
                    )

                )
            )
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}
