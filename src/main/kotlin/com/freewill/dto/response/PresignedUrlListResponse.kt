package com.freewill.dto.response

data class PresignedUrlListResponse(
    val presignedUrls: List<PresignedUrlResponse>,
    val totalCount: Int
)
