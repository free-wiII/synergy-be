package com.freewill.domain.controller

import com.freewill.controller.BookmarkGroupController
import com.freewill.docs.ApiDocumentUtils.getDocumentRequest
import com.freewill.docs.ApiDocumentUtils.getDocumentResponse
import com.freewill.docs.RestDocsTest
import com.freewill.dto.param.BookmarkGroupCreateParam
import com.freewill.dto.request.BookmarkGroupCreateRequest
import com.freewill.dto.response.BookmarkCafeListResponse
import com.freewill.dto.response.BookmarkCafeResponse
import com.freewill.dto.response.BookmarkGroupListResponse
import com.freewill.entity.User
import com.freewill.service.BookmarkGroupService
import com.freewill.service.BookmarkService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.anyLong
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BookmarkGroupController::class)
class BookmarkGroupControllerTest : RestDocsTest() {

    @MockBean
    private lateinit var bookmarkGroupService: BookmarkGroupService

    @MockBean
    private lateinit var bookmarkService: BookmarkService

    private lateinit var creatRequest: BookmarkGroupCreateRequest

    @BeforeEach
    fun setup() {
        creatRequest = BookmarkGroupCreateRequest("조용한 카페 그룹")
    }

    @Test
    fun `북마크 그룹을 생성한다`() {
        // given
        doNothing().`when`(bookmarkGroupService).save(any(BookmarkGroupCreateParam::class.java))

        // when
        val result: ResultActions = mockMvc.perform(
            post("/api/v1/bookmark-groups")
                .content(toJson(creatRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "access token")
                .characterEncoding("UTF-8")
        )

        // then
        result.andExpect(status().isCreated)
            .andDo(
                document(
                    "bookmark-group-create",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestHeaders(
                        headerWithName("Authorization").description("액세스 토큰")
                    ),
                    requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("북마크 그룹 제목")
                    ),
                    responseFields(
                        fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터").ignored()
                    )
                )
            )
    }

    @Test
    fun `북마크 그룹 리스트 조회에 성공한다`() {
        // given
        val response = BookmarkGroupListResponse(
            bookmarkGroups = listOf("조용한 카페 모음", "갬성 카페 모음", "이쁜 사람이 많은 카페 모음"),
            totalCount = 3
        )

        given(bookmarkGroupService.findAll(any(User::class.java)))
            .willReturn(response)

        // when
        val result: ResultActions = mockMvc.perform(
            get("/api/v1/bookmark-groups")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "access token")
                .characterEncoding("UTF-8")
        )

        // then
        result.andExpect(status().isOk)
            .andDo(
                document(
                    "bookmark-group-list",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestHeaders(
                        headerWithName("Authorization").description("액세스 토큰")
                    ),
                    responseFields(
                        fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                        fieldWithPath("data.bookmarkGroups").type(JsonFieldType.ARRAY).description("북마크 그룹 이름들"),
                        fieldWithPath("data.totalCount").type(JsonFieldType.NUMBER).description("총 개수"),
                    )
                )
            )
    }

    @Test
    fun `해당 북마크에 속한 카페 리스트 조회에 성공한다`() {
        // given
        val response = BookmarkCafeListResponse(
            bookmarkCafes = getBookmarkCafeResponse(),
            totalCount = 3
        )

        given(bookmarkService.findAllByBookmarkGroupId(anyLong()))
            .willReturn(response)

        // when
        val result: ResultActions = mockMvc.perform(
            get("/api/v1/bookmark-groups/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "access token")
                .characterEncoding("UTF-8")
        )

        // then
        result.andExpect(status().isOk)
            .andDo(
                document(
                    "bookmark-group-detail",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestHeaders(
                        headerWithName("Authorization").description("액세스 토큰")
                    ),
                    pathParameters(
                        parameterWithName("id").description("북마크 그룹 Id")
                    ),
                    responseFields(
                        fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                        fieldWithPath("data.bookmarkCafes").type(JsonFieldType.ARRAY).description("북마크 카페들"),
                        fieldWithPath("data.bookmarkCafes[].name").type(JsonFieldType.STRING).description("이름"),
                        fieldWithPath("data.bookmarkCafes[].address").type(JsonFieldType.STRING).description("주소"),
                        fieldWithPath("data.bookmarkCafes[].imageUris").type(JsonFieldType.ARRAY).description("이미지"),
                        fieldWithPath("data.totalCount").type(JsonFieldType.NUMBER).description("총 개수"),
                    )
                )
            )
    }

    private fun getBookmarkCafeResponse(): List<BookmarkCafeResponse> {
        return listOf(
            BookmarkCafeResponse(
                name = "카페1",
                address = "주소1",
                imageUris = listOf("이미지1", "이미지2", "이미지3")
            ),
            BookmarkCafeResponse(
                name = "카페2",
                address = "주소2",
                imageUris = listOf("이미지4", "이미지5", "이미지6")
            ),
            BookmarkCafeResponse(
                name = "카페3",
                address = "주소3",
                imageUris = listOf("이미지7", "이미지8", "이미지9")
            ),
        )
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}
