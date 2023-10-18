package com.freewill.dto.response

import com.querydsl.core.annotations.QueryProjection

data class GuestbookSimpleResponse @QueryProjection constructor(
    val username: String,
    val content: String
)
