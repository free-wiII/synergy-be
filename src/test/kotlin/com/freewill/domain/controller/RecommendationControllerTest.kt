package com.freewill.domain.controller

import com.freewill.controller.RecommendationController
import com.freewill.docs.ApiDocumentUtils.getDocumentRequest
import com.freewill.docs.ApiDocumentUtils.getDocumentResponse
import com.freewill.docs.RestDocsTest
import com.freewill.dto.param.RecommendationUpdateParam
import com.freewill.entity.Cafe
import com.freewill.entity.User
import com.freewill.enums.Provider
import com.freewill.service.CafeService
import com.freewill.service.RecommendationService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.doNothing
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean

import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

@WebMvcTest(RecommendationController::class)
class RecommendationControllerTest : RestDocsTest() {

    @MockBean
    private lateinit var recommendationService: RecommendationService

    @MockBean
    private lateinit var cafeService: CafeService

    private lateinit var user: User

    private lateinit var cafe: Cafe

    @BeforeEach
    fun setup() {
        user = User(
            provider = Provider.KAKAO,
            providerId = "test id",
            providerNickname = "test name",
            providerEmail = "test email"
        )

        cafe = Cafe(
            name = "카페",
            content = "설명",
            address = "주소",
            reviewUri = "리뷰 uri",
            latitude = BigDecimal(1.1111),
            longitude = BigDecimal(1.1111)
        )
    }

    @Test
    fun `추천 상태를 업데이트 한다`() {
        // given
        val mockId: Long = 1L
        given(cafeService.findById(mockId)).willReturn(cafe)
        doNothing().`when`(recommendationService).update(RecommendationUpdateParam(user, cafe))

        // when
        val result: ResultActions = mockMvc.perform(
            put("/api/v1/cafes/{id}/recommendations", mockId)
                .contentType(APPLICATION_JSON)
                .header("Authorization", "access token")
                .characterEncoding("UTF-8")
        )

        // then
        result.andExpect(status().isOk)
            .andDo(
                document(
                    "recommendation-update",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestHeaders(
                        headerWithName("Authorization").description("액세스 토큰")
                    ),
                    pathParameters(
                        parameterWithName("id").description("카페 Id")
                    ),
                    responseFields(
                        fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                        fieldWithPath("data").type(JsonFieldType.STRING).description("응답 데이터").ignored()
                    )
                )
            )
    }

}
