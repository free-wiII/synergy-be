package com.freewill.dto.response

import com.querydsl.core.annotations.QueryProjection

data class BookmarkCafeResponse @QueryProjection constructor(
    val name: String,
    val address: String,
    val imageUris: List<String>
)
