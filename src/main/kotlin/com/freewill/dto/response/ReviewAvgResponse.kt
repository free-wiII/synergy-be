package com.freewill.dto.response

import com.freewill.enums.ReviewType
import com.querydsl.core.annotations.QueryProjection

data class ReviewAvgResponse @QueryProjection constructor(
    val type: ReviewType,
    val point: Double
)
