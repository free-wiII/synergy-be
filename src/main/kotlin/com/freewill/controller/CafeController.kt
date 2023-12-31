package com.freewill.controller

import com.freewill.common.annotation.AuthorizedUser
import com.freewill.common.response.ApiResponse
import com.freewill.dto.request.CafeCreateRequest
import com.freewill.dto.response.CafeDetailResponse
import com.freewill.entity.User
import com.freewill.enums.SuccessMessage
import com.freewill.service.CafeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/api/v1/cafes")
@RestController
class CafeController(
    private val cafeService: CafeService
) {

    @PostMapping
    fun register(
        @RequestPart(value = "images", required = false) images: List<MultipartFile>?,
        @RequestPart(value = "cafeCreateRequest") request: CafeCreateRequest
    ): ResponseEntity<ApiResponse<Void>> {
        cafeService.save(request.toParam(images))
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.createSuccess(SuccessMessage.SUCCESS_REGISTER_CAFE.msg))
    }

    @GetMapping("/{id}")
    fun details(@PathVariable id: Long,
                @AuthorizedUser user: User?): ApiResponse<CafeDetailResponse> {
        return ApiResponse.createSuccessWithData(
            SuccessMessage.SUCCESS_SEARCH_CAFE.msg,
            cafeService.findCafeDetail(id, user)
        )
    }
}
