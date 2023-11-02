package com.freewill.controller

import com.freewill.common.annotation.AuthorizedUser
import com.freewill.common.response.ApiResponse
import com.freewill.dto.request.BookmarkGroupCreateRequest
import com.freewill.dto.response.BookmarkGroupListResponse
import com.freewill.entity.User
import com.freewill.enums.SuccessMessage
import com.freewill.service.BookmarkGroupService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/bookmark-groups")
class BookmarkGroupController(
    private val bookmarkGroupService: BookmarkGroupService
) {
    @PostMapping
    fun create(
        @RequestBody request: BookmarkGroupCreateRequest,
        @AuthorizedUser user: User
    ): ResponseEntity<ApiResponse<Void>> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.createSuccess(SuccessMessage.SUCCESS_CREATE_BOOKMARK_GROUP.msg))
    }

    @GetMapping
    fun list(@AuthorizedUser user: User): ApiResponse<BookmarkGroupListResponse> {
        return ApiResponse.createSuccessWithData(
            SuccessMessage.SUCCESS_SEARCH_BOOKMARK_GROUP.msg,
            bookmarkGroupService.findAll(user)
        )
    }

    @GetMapping("/{id}")
    fun detail(@PathVariable id: String) {

    }
}
