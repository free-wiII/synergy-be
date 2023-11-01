package com.freewill.controller

import com.freewill.common.annotation.AuthorizedUser
import com.freewill.common.response.ApiResponse
import com.freewill.dto.request.GuestbookCreateRequest
import com.freewill.dto.response.GuestbookListResponse
import com.freewill.entity.Cafe
import com.freewill.entity.User
import com.freewill.enums.SuccessMessage
import com.freewill.service.CafeService
import com.freewill.service.GuestbookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/guestbooks")
class GuestbookController(
    private val cafeService: CafeService,
    private val guestbookService: GuestbookService,
) {
    @PostMapping
    fun register(
        @AuthorizedUser user: User,
        @RequestBody request: GuestbookCreateRequest
    ): ResponseEntity<ApiResponse<Void>> {
        val cafe: Cafe = cafeService.findById(request.cafeId)
        guestbookService.save(request.toParam(user, cafe))

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.createSuccess(SuccessMessage.SUCCESS_REGISTER_GUESTBOOK.msg))
    }

    @GetMapping
    fun list(@RequestParam(value = "cafeId") cafeId: Long): ApiResponse<GuestbookListResponse> {
        return ApiResponse.createSuccessWithData(
            SuccessMessage.SUCCESS_SEARCH_GUESTBOOKS.msg,
            guestbookService.findByCafe(cafeId, true)
        )
    }
}
