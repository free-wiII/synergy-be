package com.freewill.domain.controller

import com.freewill.controller.PresignedUrlController
import com.freewill.docs.ApiDocumentUtils
import com.freewill.docs.ApiDocumentUtils.getDocumentRequest
import com.freewill.docs.ApiDocumentUtils.getDocumentResponse
import com.freewill.docs.RestDocsTest
import com.freewill.dto.request.UploadPresignedUrlRequest
import com.freewill.dto.response.PresignedUrlListResponse
import com.freewill.dto.response.PresignedUrlResponse
import com.freewill.enums.DomainType
import com.freewill.service.PresignedUrlService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@WebMvcTest(PresignedUrlController::class)
class PresignedUrlControllerTest : RestDocsTest() {

    @MockBean
    private lateinit var presignedUrlService: PresignedUrlService

    private lateinit var request: UploadPresignedUrlRequest

    private lateinit var response: PresignedUrlListResponse

    @BeforeEach
    fun setup() {
        request = UploadPresignedUrlRequest(
            domain = DomainType.CAFE,
            imageNames = listOf("test1", "test2", "test3")
        )

        response = PresignedUrlListResponse(
            presignedUrls = listOf(
                getPresignedUrlResponse(DomainType.CAFE, "test1"),
                getPresignedUrlResponse(DomainType.CAFE, "test2"),
                getPresignedUrlResponse(DomainType.CAFE, "test3")
            ),
            totalCount = 3
        )
    }

    @Test
    fun `업로드용 Presigned Url을 발급한다`() {
        // given
        given(presignedUrlService.getUploadPresignedUrls(request))
            .willReturn(response)

        val result: ResultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.post("/api/v1/presigned-urls/upload")
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "access token")
                .characterEncoding("UTF-8")
        )

        // then
        result.andExpect(status().isCreated)
            .andDo(
                document(
                    "presignedUrl-upload",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestHeaders(
                        headerWithName("Authorization").description("액세스 토큰")
                    ),
                    requestFields(
                        fieldWithPath("domain").type(JsonFieldType.STRING).description("이미지 도메인"),
                        fieldWithPath("imageNames[]").type(JsonFieldType.ARRAY).description("이미지 이름 리스트"),
                    ),
                    responseFields(
                        fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                        fieldWithPath("data.presignedUrls").type(JsonFieldType.ARRAY).description("Presigned Url 리스트"),
                        fieldWithPath("data.presignedUrls[].presignedUrl").type(JsonFieldType.STRING).description("Presigned Url"),
                        fieldWithPath("data.presignedUrls[].filename").type(JsonFieldType.STRING).description("새로운 이미지 이름"),
                        fieldWithPath("data.totalCount").type(JsonFieldType.NUMBER).description("총 데이터 개수"),
                    )
                )
            )
    }

    private fun getPresignedUrlResponse(domain: DomainType, filename: String): PresignedUrlResponse {
        val newFilename = "$filename-${LocalDateTime.now()}"
        val path = "${domain.toLowerCase()}/$newFilename"
        return PresignedUrlResponse(
            "presignedUrl/$path",
            newFilename
        )
    }
}
