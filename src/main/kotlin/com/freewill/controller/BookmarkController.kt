package com.freewill.controller

import com.freewill.common.response.ApiResponse
import com.freewill.dto.request.BookmarkUpdateRequest
import com.freewill.enums.SuccessMessage
import com.freewill.service.BookmarkService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/cafes/{id}")
class BookmarkController(
    private val bookmarkService: BookmarkService
) {
    @PutMapping("/bookmarks")
    fun update(@PathVariable id: Long,
               @RequestBody request: BookmarkUpdateRequest): ApiResponse<Void> {
        bookmarkService.update(request.toParam(id))

        return ApiResponse.createSuccess(SuccessMessage.SUCCESS_UPDATE_BOOKMARK_GROUP.msg)
    }
}
