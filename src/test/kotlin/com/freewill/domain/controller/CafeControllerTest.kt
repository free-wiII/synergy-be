package com.freewill.domain.controller

import com.freewill.controller.CafeController
import com.freewill.docs.ApiDocumentUtils.getDocumentRequest
import com.freewill.docs.ApiDocumentUtils.getDocumentResponse
import com.freewill.docs.RestDocsTest
import com.freewill.dto.param.CafeCreateParam
import com.freewill.dto.request.CafeCreateRequest
import com.freewill.dto.request.ReviewCreateRequest
import com.freewill.dto.response.CafeDetailResponse
import com.freewill.dto.response.GuestbookSimpleResponse
import com.freewill.dto.response.ReviewAvgResponse
import com.freewill.entity.Cafe
import com.freewill.entity.User
import com.freewill.enums.ReviewType
import com.freewill.service.CafeService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.anyLong
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.anyList
import org.mockito.Mockito.doNothing
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.mock.web.MockMultipartFile
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

@WebMvcTest(CafeController::class)
class CafeControllerTest : RestDocsTest() {

    @MockBean
    private lateinit var cafeService: CafeService

    private lateinit var cafe: Cafe

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
    }

    @Test
    fun `카페 등록에 성공한다`() {
        // given
        val images: List<MockMultipartFile> = listOf(
            getMockMultiPartFile("1"),
            getMockMultiPartFile("2"),
            getMockMultiPartFile("3")
        )
        val request: CafeCreateRequest = CafeCreateRequest(
            name = "cafe",
            content = "커피 존맛인 카페",
            address = " 우리집",
            reviewUri = "리뷰 uri",
            latitude = BigDecimal(13.12351235),
            longitude = BigDecimal(13.12351235)
        )

        doNothing().`when`(cafeService).save(any(CafeCreateParam::class.java))

        // when
        val result: ResultActions = mockMvc.perform(
            multipart("/api/v1/cafes")
                .file(images[0])
                .file(images[1])
                .file(images[2])
                .file(toMultiPartFile("cafeCreateRequest", objectMapper.writeValueAsString(request)))
                .contentType("multipart/form-data")
                .accept(APPLICATION_JSON)
                .header("Authorization", "access token")
                .characterEncoding("UTF-8")
        )

        // then
        result.andExpect(status().isCreated)
            .andDo(
                document(
                    "cafe-register",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestHeaders(
                        headerWithName("Authorization").description("액세스 토큰")
                    ),
                    requestPartFields(
                        "cafeCreateRequest",
                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("소개").optional(),
                        fieldWithPath("address").type(JsonFieldType.STRING).description("주소"),
                        fieldWithPath("reviewUri").type(JsonFieldType.STRING).description("리뷰 url").optional(),
                        fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("위도"),
                        fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("경도")
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
    fun `카페 상세조회에 성공한다`() {
        // given
        val response: CafeDetailResponse = CafeDetailResponse(
            imageUris = listOf("url1", "url2"),
            name = "summer brick",
            content = "광명 카페",
            address = "21, Yangji-ro, Gwangmyeong",
            reviewUri = "review.uri/vhuiq?search=hqwq",
            recommendationCount = 12,
            recommendationFlag = true,
            bookmarkFlag = true,
            reviews = listOf(
                ReviewAvgResponse(ReviewType.MOOD, 4.5),
                ReviewAvgResponse(ReviewType.BEVERAGE, 4.1),
                ReviewAvgResponse(ReviewType.DESERT, 3.5),
                ReviewAvgResponse(ReviewType.GROUP, 4.2),
                ReviewAvgResponse(ReviewType.QUIET, 1.5),
                ReviewAvgResponse(ReviewType.CONSENT, 3.4)
            ),
            guestbooks = listOf(
                GuestbookSimpleResponse("user1", "커피 마싯어"),
                GuestbookSimpleResponse("user2", "케잌 마싯어"),
                GuestbookSimpleResponse("user3", "사람 많아")
            )
        )
        given(cafeService.findCafeDetail(anyLong(), any(User::class.java)))
            .willReturn(response)

        // when
        val result: ResultActions = mockMvc.perform(
            get("/api/v1/cafes/{id}", 1L)
                .contentType(APPLICATION_JSON)
                .header("Authorization", "access token")
                .characterEncoding("UTF-8")
        )

        // then
        result.andExpect(status().isOk)
            .andDo(
                document(
                    "cafe-detail",
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
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                        fieldWithPath("data.imageUris").type(JsonFieldType.ARRAY).description("이미지 uri 리스트"),
                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("카페 이름"),
                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("카페 소개").optional(),
                        fieldWithPath("data.address").type(JsonFieldType.STRING).description("카페 주소"),
                        fieldWithPath("data.reviewUri").type(JsonFieldType.STRING).description("카페 리뷰 uri")
                            .optional(),
                        fieldWithPath("data.recommendationCount").type(JsonFieldType.NUMBER).description("추천")
                            .optional(),
                        fieldWithPath("data.recommendationFlag").type(JsonFieldType.BOOLEAN).description("추천 여부")
                            .optional(),
                        fieldWithPath("data.bookmarkFlag").type(JsonFieldType.BOOLEAN).description("북마크 여부").optional(),
                        fieldWithPath("data.reviews").type(JsonFieldType.ARRAY).description("각 리뷰 점수들"),
                        fieldWithPath("data.reviews[].type").type(JsonFieldType.STRING).description("리뷰 타입"),
                        fieldWithPath("data.reviews[].point").type(JsonFieldType.NUMBER).description("리뷰 점수"),
                        fieldWithPath("data.guestbooks").type(JsonFieldType.ARRAY).description("방명록 리스트"),
                        fieldWithPath("data.guestbooks[].username").type(JsonFieldType.STRING).description("유저 이름"),
                        fieldWithPath("data.guestbooks[].content").type(JsonFieldType.STRING).description("내용"),
                    )
                )
            )
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}
