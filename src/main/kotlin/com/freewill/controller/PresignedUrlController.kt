package com.freewill.controller

import com.freewill.common.response.ApiResponse
import com.freewill.dto.request.UploadPresignedUrlRequest
import com.freewill.dto.response.PresignedUrlListResponse
import com.freewill.enums.SuccessMessage
import com.freewill.service.PresignedUrlService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/presigned-urls")
class PresignedUrlController(
    private val presignedUrlService: PresignedUrlService
) {

    @PostMapping("/upload")
    fun getUpdatePresignedUrls(@RequestBody request: UploadPresignedUrlRequest): ResponseEntity<ApiResponse<PresignedUrlListResponse>> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                ApiResponse.createSuccessWithData(
                    SuccessMessage.SUCCESS_GET_UPLOAD_PRESIGNED_URL.msg,
                    presignedUrlService.getUploadPresignedUrls(request)
                )
            )
    }
}
