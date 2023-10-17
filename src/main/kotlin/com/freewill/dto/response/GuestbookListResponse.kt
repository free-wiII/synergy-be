package com.freewill.dto.response

data class GuestbookListResponse(
    val guestbooks: List<GuestbookSimpleResponse>,
    val totalCount: Int
)
