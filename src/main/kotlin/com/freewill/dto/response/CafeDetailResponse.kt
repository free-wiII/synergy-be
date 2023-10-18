package com.freewill.dto.response

data class CafeDetailResponse(
    val imageUris: List<String>,
    val name: String,
    val content: String?,
    val address: String,
    val reviewUri: String?,
    val reviews: List<ReviewAvgResponse>,
    val guestbooks: List<GuestbookSimpleResponse>
)
