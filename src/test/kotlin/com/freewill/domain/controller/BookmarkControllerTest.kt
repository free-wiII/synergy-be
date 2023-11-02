package com.freewill.domain.controller

import com.freewill.controller.BookmarkController
import com.freewill.docs.ApiDocumentUtils.getDocumentRequest
import com.freewill.docs.ApiDocumentUtils.getDocumentResponse
import com.freewill.docs.RestDocsTest
import com.freewill.dto.param.BookmarkUpdateParam
import com.freewill.dto.request.BookmarkUpdateRequest
import com.freewill.entity.Cafe
import com.freewill.service.BookmarkService
import com.freewill.service.CafeService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

@WebMvcTest(BookmarkController::class)
class BookmarkControllerTest : RestDocsTest() {

    @MockBean
    private lateinit var bookmarkService: BookmarkService

    @MockBean
    private lateinit var cafeService: CafeService

    private lateinit var request: BookmarkUpdateRequest

    private lateinit var cafe: Cafe

    @BeforeEach
    fun setup() {
        request = BookmarkUpdateRequest(1)

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
    fun `북마크 등록에 성공한다`() {
        // given
        val mockId = 1L
        given(cafeService.findById(mockId)).willReturn(cafe)
        doNothing().`when`(bookmarkService).update(any(BookmarkUpdateParam::class.java))

        // when
        val result: ResultActions = mockMvc.perform(
            put("/api/v1/cafes/{id}/bookmarks", 1L)
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "access token")
                .characterEncoding("UTF-8")
        )

        // then
        result.andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "bookmark-update",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestHeaders(
                        headerWithName("Authorization").description("액세스 토큰")
                    ),
                    pathParameters(
                        parameterWithName("id").description("카페 Id")
                    ),
                    requestFields(
                        fieldWithPath("bookmarkGroupId").type(JsonFieldType.NUMBER).description("북마크 그룹 ID")
                    ),
                    responseFields(
                        fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터").ignored()
                    )
                )
            )
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}
