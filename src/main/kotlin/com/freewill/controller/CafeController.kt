package com.freewill.controller

import com.freewill.common.response.ApiResponse
import com.freewill.dto.request.CafeCreateRequest
import com.freewill.dto.response.CafeDetailResponse
import com.freewill.enums.ResponseMessage
import com.freewill.service.CafeService
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
    fun register(@RequestPart(value = "images", required = false) images: List<MultipartFile>,
                 @RequestPart(value = "cafeCreateRequest") request: CafeCreateRequest): ApiResponse<Void> {
        cafeService.save(request.toParam(images))
        return ApiResponse.createSuccess(ResponseMessage.SUCCESS_REGISTER_CAFE.msg)
    }

    @GetMapping("/{id}")
    fun modify(@PathVariable id: Long): ApiResponse<CafeDetailResponse> {
        return ApiResponse.createSuccessWithData(
            ResponseMessage.SUCCESS_SEARCH_CAFE.msg,
            cafeService.findById(id).toCafeDetailResponse()
        )
    }
}
