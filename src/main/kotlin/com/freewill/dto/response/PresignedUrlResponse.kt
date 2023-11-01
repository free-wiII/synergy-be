package com.freewill.dto.response

data class PresignedUrlResponse(
    val presignedUrl: String,
    val filename: String
)
