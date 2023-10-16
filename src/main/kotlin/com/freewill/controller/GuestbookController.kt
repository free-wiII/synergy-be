package com.freewill.controller

import com.freewill.common.annotation.AuthorizedUser
import com.freewill.common.response.ApiResponse
import com.freewill.dto.request.GuestbookCreateRequest
import com.freewill.entity.Cafe
import com.freewill.entity.Guestbook
import com.freewill.entity.User
import com.freewill.enums.ResponseMessage
import com.freewill.service.CafeService
import com.freewill.service.GuestbookService
import com.freewill.service.ReviewService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/guestbooks")
class GuestbookController(
    private val cafeService: CafeService,
    private val guestbookService: GuestbookService,
    private val reviewService: ReviewService
) {
    @PostMapping
    fun register(
        @AuthorizedUser user: User,
        @RequestBody request: GuestbookCreateRequest
    ): ApiResponse<Void> {
        val cafe: Cafe = cafeService.findById(request.cafeId)
        val guestbook: Guestbook = guestbookService.save(request.toParam(
            cafe = cafe,
            user = user
        ))

        reviewService.saveAll(request.reviews.map { it.toParam(guestbook) }.toList())

        return ApiResponse.createSuccess(ResponseMessage.SUCCESS_REGISTER_GUESTBOOK.msg)
    }
}
