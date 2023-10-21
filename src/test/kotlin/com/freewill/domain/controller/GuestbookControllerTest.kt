package com.freewill.domain.controller

import com.freewill.controller.GuestbookController
import com.freewill.docs.ApiDocumentUtils.getDocumentRequest
import com.freewill.docs.ApiDocumentUtils.getDocumentResponse
import com.freewill.docs.RestDocsTest
import com.freewill.dto.param.GuestbookCreateParam
import com.freewill.dto.request.GuestbookCreateRequest
import com.freewill.dto.request.ReviewCreateRequest
import com.freewill.dto.response.GuestbookListResponse
import com.freewill.dto.response.GuestbookSimpleResponse
import com.freewill.entity.Cafe
import com.freewill.entity.User
import com.freewill.enums.Provider
import com.freewill.service.CafeService
import com.freewill.service.GuestbookService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.queryParameters
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

@WebMvcTest(GuestbookController::class)
class GuestbookControllerTest : RestDocsTest() {

    @MockBean
    private lateinit var guestbookService: GuestbookService

    @MockBean
    private lateinit var cafeService: CafeService

    private lateinit var cafe: Cafe

    private lateinit var user: User

    @BeforeEach
    fun setup() {
        cafe = Cafe(
            name = "cafe",
            content = "커피 존맛인 카페",
            address = " 우리집",
            reviewUri = "리뷰 uri",
            latitude = BigDecimal(13.12351235),
            longitude = BigDecimal(13.12351235)
        )
        user = User(
            provider = Provider.KAKAO,
            providerId = "test id",
            providerNickname = "test name",
            providerEmail = "test email"
        )
    }

    @Test
    fun `방명록 등록에 성공한다`() {
        // given
        val request: GuestbookCreateRequest = GuestbookCreateRequest(
            cafeId = 1L,
            content = "커피 마싯다",
            reviews = mutableListOf(
                ReviewCreateRequest("MOOD", 4.5),
                ReviewCreateRequest("BEVERAGE", 4.1),
                ReviewCreateRequest("DESERT", 3.5),
                ReviewCreateRequest("GROUP", 4.2),
                ReviewCreateRequest("QUIET", 1.5),
                ReviewCreateRequest("CONSENT", 3.4)

            )
        )

        given(cafeService.findById(anyLong()))
            .willReturn(cafe)
        doNothing().`when`(guestbookService).save(any(GuestbookCreateParam::class.java))

        // when
        val result: ResultActions = mockMvc.perform(
            post("/api/v1/guestbooks")
                .content(toJson(request))
                .contentType(APPLICATION_JSON)
                .header("Authorization", "access token")
                .characterEncoding("UTF-8")
        )

        // then
        result.andExpect(status().isCreated)
            .andDo(
                document(
                    "guestbook-register",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestHeaders(
                        headerWithName("Authorization").description("액세스 토큰")
                    ),
                    requestFields(
                        fieldWithPath("cafeId").type(JsonFieldType.NUMBER).description("카페 ID"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                        fieldWithPath("reviews[]").type(JsonFieldType.ARRAY).description("리뷰 리스트"),
                        fieldWithPath("reviews[].type").type(JsonFieldType.STRING).description("리뷰 타입"),
                        fieldWithPath("reviews[].point").type(JsonFieldType.NUMBER).description("리뷰 점수")
                    ),
                    responseFields(
                        fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                        fieldWithPath("data").type(JsonFieldType.STRING).description("응답 데이터").ignored()
                    )
                )
            )
    }

    @Test
    fun `카페의 방명록 다건 조회에 성공한다`() {
        // given
        val mockId: Long = 1L
        val response: GuestbookListResponse = GuestbookListResponse(
            guestbooks = listOf(
                GuestbookSimpleResponse("user1", "커피 마싯어"),
                GuestbookSimpleResponse("user2", "케잌 마싯어"),
                GuestbookSimpleResponse("user3", "사람 많아")
            ),
            totalCount = 3
        )
        given(guestbookService.findByCafe(anyLong(), anyBoolean()))
            .willReturn(response)

        // when
        val result: ResultActions = mockMvc.perform(
            get("/api/v1/guestbooks")
                .param("cafeId", mockId.toString())
                .contentType(APPLICATION_JSON)
                .header("Authorization", "access token")
                .characterEncoding("UTF-8")
        )

        // then
        result.andExpect(status().isOk)
            .andDo(
                document(
                    "guestbook-list",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestHeaders(
                        headerWithName("Authorization").description("액세스 토큰")
                    ),
                    queryParameters(
                        parameterWithName("cafeId").description("카페 ID")
                    ),
                    responseFields(
                        fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                        fieldWithPath("data.guestbooks").type(JsonFieldType.ARRAY).description("방명록 리스트"),
                        fieldWithPath("data.guestbooks[].username").type(JsonFieldType.STRING).description("유저 이름"),
                        fieldWithPath("data.guestbooks[].content").type(JsonFieldType.STRING).description("내용"),
                        fieldWithPath("data.totalCount").type(JsonFieldType.NUMBER).description("총 개수")
                    )
                )
            )
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}
