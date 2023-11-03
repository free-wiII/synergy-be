package com.freewill.controller

import com.freewill.common.response.ApiResponse
import com.freewill.dto.request.BookmarkUpdateRequest
import com.freewill.entity.Cafe
import com.freewill.enums.SuccessMessage
import com.freewill.service.BookmarkService
import com.freewill.service.CafeService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/cafes/{id}/bookmarks")
class BookmarkController(
    private val bookmarkService: BookmarkService,
    private val cafeService: CafeService
) {
    @PutMapping
    fun update(@PathVariable id: Long,
               @RequestBody request: BookmarkUpdateRequest): ApiResponse<Void> {
        val cafe: Cafe = cafeService.findById(id)
        bookmarkService.update(request.toParam(cafe))

        return ApiResponse.createSuccess(SuccessMessage.SUCCESS_UPDATE_BOOKMARK_GROUP.msg)
    }
}
