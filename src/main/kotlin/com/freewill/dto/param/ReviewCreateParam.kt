package com.freewill.dto.param

import com.freewill.entity.Guestbook
import com.freewill.entity.Review
import com.freewill.enums.ReviewType

data class ReviewCreateParam(
    val type: ReviewType,
    val point: Double,
    val guestbook: Guestbook
) {
    fun toEntity() = Review(
        point = point,
        type = type,
        guestbook = guestbook
    )
}
