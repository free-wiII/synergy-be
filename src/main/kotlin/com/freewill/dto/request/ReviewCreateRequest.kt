package com.freewill.dto.request

import com.freewill.dto.param.ReviewCreateParam
import com.freewill.entity.Guestbook
import com.freewill.enums.ReviewType

data class ReviewCreateRequest(
    val type: String,
    val point: Double
) {
    fun toParam(guestbook: Guestbook): ReviewCreateParam = ReviewCreateParam(
        type = ReviewType.valueOf(type),
        point = point,
        guestbook = guestbook
    )
}
