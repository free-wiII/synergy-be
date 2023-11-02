package com.freewill.dto.response

data class BookmarkCafeListResponse(
    val bookmarkCafes: List<BookmarkCafeResponse>,
    val totalCount: Int
)
